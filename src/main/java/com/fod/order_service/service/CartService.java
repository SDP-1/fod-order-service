package com.fod.order_service.service;

import com.fod.order_service.client.RestaurantClient;
import com.fod.order_service.dto.*;
import com.fod.order_service.entity.Cart;
import com.fod.order_service.entity.CartItem;
import com.fod.order_service.repositoty.CartRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class CartService {

    private static final Logger logger = Logger.getLogger(CartService.class.getName());

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private RestaurantClient restaurantClient;
    @Autowired
    private ModelMapper modelMapper;

    public CartResponseDTO getCart(String userId) {
        Optional<Cart> cartOpt = cartRepository.findByUserIdAndIsActive(userId, true);
        Cart cart = cartOpt.orElseGet(() -> Cart.builder()
                .userId(userId)
                .items(new ArrayList<>())
                .build());

        CartResponseDTO response = new CartResponseDTO();
        response.setId(cart.getId());
        response.setUserId(cart.getUserId());
        response.setCreatedAt(cart.getCreatedAt());
        response.setUpdatedAt(cart.getUpdatedAt());
        response.setActive(cart.isActive());

        // Map items to CartItemResponseDTO
        List<CartItemResponseDTO> responseItems = new ArrayList<>();
        for (CartItem item : cart.getItems()) {
            MenuItemResponseDTO menuItem = restaurantClient.getMenuItemById(item.getMenuItemId());
            if (menuItem != null) {
                CartItemResponseDTO itemDTO = new CartItemResponseDTO();
                itemDTO.setMenuItem(menuItem);
                itemDTO.setQuantity(item.getQuantity());
                itemDTO.setCustomizations(item.getCustomizations() != null ? item.getCustomizations() : new ArrayList<>());
                itemDTO.setTotalPrice(calculateTotalPrice(menuItem, item.getQuantity()));
                itemDTO.setTotalDiscount(calculateTotalDiscount(menuItem, item.getQuantity()));
                itemDTO.setNetTotalPrice(calculateNetTotalPrice(menuItem, item.getQuantity()));
                responseItems.add(itemDTO);
            }
        }
        response.setItems(responseItems);

        // Set restaurant
        if (cart.getRestaurantId() != null) {
            RestaurantResponseDTO restaurant = restaurantClient.getRestaurantById(cart.getRestaurantId());
            response.setRestaurant(restaurant);
        }

        return response;
    }

    public CartResponseDTO addToCart(String userId, CartItemRequestDTO requestDTO) {
        // Validate input
        if (requestDTO == null || requestDTO.getMenuItemId() == null || requestDTO.getQuantity() == null || requestDTO.getQuantity() < 1) {
            throw new IllegalArgumentException("Invalid cart item request");
        }

        logger.info("Fetching menu item: " + requestDTO.getMenuItemId());
        // Fetch menu item details
        MenuItemResponseDTO menuItem = restaurantClient.getMenuItemById(requestDTO.getMenuItemId());
        if (menuItem == null) {
            throw new IllegalArgumentException("Menu item not found: " + requestDTO.getMenuItemId());
        }

        logger.info("Menu item fetched: " + menuItem.getName() + ", price: " + menuItem.getPrice());

        // Check for any existing cart (active or inactive)
        Optional<Cart> cartOpt = cartRepository.findByUserId(userId);
        Cart cart = cartOpt.orElseGet(() -> Cart.builder()
                .userId(userId)
                .items(new ArrayList<>())
                .build());

        // Reactivate if inactive
        if (!cart.isActive()) {
            cart.setActive(true);
        }

        // Ensure items list is initialized
        if (cart.getItems() == null) {
            logger.warning("Cart items list was null, initializing");
            cart.setItems(new ArrayList<>());
        }

        // Validate restaurant consistency
        if (cart.getRestaurantId() != null && !cart.getRestaurantId().equals(menuItem.getRestaurantId())) {
            throw new IllegalArgumentException("Cannot add items from different restaurants");
        }

        cart.setRestaurantId(menuItem.getRestaurantId());
        Optional<CartItem> existingItem = cart.getItems().stream()
                .filter(item -> item.getMenuItemId().equals(requestDTO.getMenuItemId())
                        && (item.getCustomizations() == null ? requestDTO.getCustomizations() == null :
                        item.getCustomizations().equals(requestDTO.getCustomizations())))
                .findFirst();

        if (existingItem.isPresent()) {
            CartItem item = existingItem.get();
            int newQuantity = item.getQuantity() + requestDTO.getQuantity();
            item.setQuantity(newQuantity);
            logger.info("Updated existing item: " + item.getMenuItemId());
        } else {
            CartItem newItem = CartItem.builder()
                    .menuItemId(requestDTO.getMenuItemId())
                    .quantity(requestDTO.getQuantity())
                    .customizations(requestDTO.getCustomizations() != null ? requestDTO.getCustomizations() : new ArrayList<>())
                    .build();
            logger.info("Adding new item: " + newItem);
            cart.getItems().add(newItem);
        }

        logger.info("Saving cart with items: " + cart.getItems().size());
        Cart savedCart = cartRepository.save(cart);

        CartResponseDTO response = new CartResponseDTO();
        response.setId(savedCart.getId());
        response.setUserId(savedCart.getUserId());
        response.setCreatedAt(savedCart.getCreatedAt());
        response.setUpdatedAt(savedCart.getUpdatedAt());
        response.setActive(savedCart.isActive());

        // Map items to CartItemResponseDTO
        List<CartItemResponseDTO> responseItems = new ArrayList<>();
        for (CartItem item : savedCart.getItems()) {
            MenuItemResponseDTO itemMenu = restaurantClient.getMenuItemById(item.getMenuItemId());
            if (itemMenu != null) {
                CartItemResponseDTO itemDTO = new CartItemResponseDTO();
                itemDTO.setMenuItem(itemMenu);
                itemDTO.setQuantity(item.getQuantity());
                itemDTO.setCustomizations(item.getCustomizations() != null ? item.getCustomizations() : new ArrayList<>());
                itemDTO.setTotalPrice(calculateTotalPrice(itemMenu, item.getQuantity()));
                itemDTO.setTotalDiscount(calculateTotalDiscount(itemMenu, item.getQuantity()));
                itemDTO.setNetTotalPrice(calculateNetTotalPrice(itemMenu, item.getQuantity()));
                responseItems.add(itemDTO);
            }
        }
        response.setItems(responseItems);

        // Set restaurant
        if (savedCart.getRestaurantId() != null) {
            RestaurantResponseDTO restaurant = restaurantClient.getRestaurantById(savedCart.getRestaurantId());
            response.setRestaurant(restaurant);
        }

        return response;
    }

    public void clearCart(String userId) {
        Optional<Cart> cartOpt = cartRepository.findByUserIdAndIsActive(userId, true);
        cartOpt.ifPresent(cart -> {
            cart.setItems(new ArrayList<>());
            cart.setRestaurantId(null);
            cart.setActive(false);
            cartRepository.save(cart);
        });
    }

    public CartResponseDTO removeItemById(String userId, String menuItemId) {
        logger.info("Removing item: " + menuItemId + " for user: " + userId);

        Optional<Cart> cartOpt = cartRepository.findByUserIdAndIsActive(userId, true);
        if (cartOpt.isEmpty()) {
            throw new IllegalArgumentException("No active cart found for user: " + userId);
        }

        Cart cart = cartOpt.get();
        if (cart.getItems() == null) {
            cart.setItems(new ArrayList<>());
        }

        if (cart.getItems().size() == 1 && cart.getItems().get(0).getMenuItemId().equals(menuItemId)) {
            logger.info("Cart has only one item, deleting cart for user: " + userId);
            clearCart(userId);
            return new CartResponseDTO(null, userId, null, new ArrayList<>(), null, null, true);
        }

        boolean removed = cart.getItems().removeIf(item -> item.getMenuItemId().equals(menuItemId));
        if (!removed) {
            throw new IllegalArgumentException("Item not found in cart: " + menuItemId);
        }

        logger.info("Saving cart with items: " + cart.getItems().size());
        Cart savedCart = cartRepository.save(cart);

        CartResponseDTO response = new CartResponseDTO();
        response.setId(savedCart.getId());
        response.setUserId(savedCart.getUserId());
        response.setCreatedAt(savedCart.getCreatedAt());
        response.setUpdatedAt(savedCart.getUpdatedAt());
        response.setActive(savedCart.isActive());

        List<CartItemResponseDTO> responseItems = new ArrayList<>();
        for (CartItem item : savedCart.getItems()) {
            MenuItemResponseDTO itemMenu = restaurantClient.getMenuItemById(item.getMenuItemId());
            if (itemMenu != null) {
                CartItemResponseDTO itemDTO = new CartItemResponseDTO();
                itemDTO.setMenuItem(itemMenu);
                itemDTO.setQuantity(item.getQuantity());
                itemDTO.setCustomizations(item.getCustomizations() != null ? item.getCustomizations() : new ArrayList<>());
                itemDTO.setTotalPrice(calculateTotalPrice(itemMenu, item.getQuantity()));
                itemDTO.setTotalDiscount(calculateTotalDiscount(itemMenu, item.getQuantity()));
                itemDTO.setNetTotalPrice(calculateNetTotalPrice(itemMenu, item.getQuantity()));
                responseItems.add(itemDTO);
            }
        }
        response.setItems(responseItems);

        if (savedCart.getRestaurantId() != null) {
            RestaurantResponseDTO restaurant = restaurantClient.getRestaurantById(savedCart.getRestaurantId());
            response.setRestaurant(restaurant);
        }

        return response;
    }

    private Double calculateTotalPrice(MenuItemResponseDTO menuItem, int quantity) {
        Double price = menuItem.getPrice() != null ? menuItem.getPrice() : 0.0;
        return price * quantity;
    }

    private Double calculateTotalDiscount(MenuItemResponseDTO menuItem, int quantity) {
        if (menuItem.getPrice() == null || menuItem.getDiscountPrice() == null) {
            return 0.0;
        }
        return (menuItem.getPrice() - menuItem.getDiscountPrice()) * quantity;
    }

    private Double calculateNetTotalPrice(MenuItemResponseDTO menuItem, int quantity) {
        return calculateTotalPrice(menuItem, quantity) - calculateTotalDiscount(menuItem, quantity);
    }
}