Marketplace
=========

A Spring Boot marketplace backend application providing RestAPIs for the following:

- user creation and authorization
- item viewing and creation
- cart creation and management
- order creation from cart
- sales management through changing order status
- viewing purchases status

## Project structure (top-level)
-----------------------------
```text
|-- pom.xml
|-- src/
|   |-- main/
|   |   |-- java/
|   |   |   |-- com/
|   |   |       |-- marketplace/
|   |   |           |-- MarketplaceApplication.java
|   |   |           |-- authentication/
|   |   |           |   |-- controller/
|   |   |           |   |   |-- AuthController.java
|   |   |           |   |-- dto/
|   |   |           |   |   |-- AuthResponse.java
|   |   |           |   |   |-- ChangePasswordRequest.java
|   |   |           |   |   |-- LoginRequest.java
|   |   |           |   |   |-- RegisterRequest.java
|   |   |           |   |-- entity/
|   |   |           |   |   |-- Role.java
|   |   |           |   |   |-- User.java
|   |   |           |   |-- exception/
|   |   |           |   |   |-- EmailAlreadyExistsException.java
|   |   |           |   |   |-- GlobalExceptionHandler.java
|   |   |           |   |-- repository/
|   |   |           |   |   |-- UserRepositoryAuthorization.java
|   |   |           |   |-- service/
|   |   |           |       |-- AuthService.java
|   |   |           |       |-- CustomUserDetailsService.java
|   |   |           |-- cart/
|   |   |           |   |-- CartController.java
|   |   |           |   |-- CartDto.java
|   |   |           |   |-- CartEntity.java
|   |   |           |   |-- CartRepository.java
|   |   |           |   |-- CartService.java
|   |   |           |   |-- CreateCartRequest.java
|   |   |           |-- cartitem/
|   |   |           |   |-- CartItemController.java
|   |   |           |   |-- CartItemDto.java
|   |   |           |   |-- CartItemEntity.java
|   |   |           |   |-- CartItemRepository.java
|   |   |           |   |-- CartItemService.java
|   |   |           |   |-- CreateCartItemRequest.java
|   |   |           |-- config/
|   |   |           |   |-- JwtAuthenticationFilter.java
|   |   |           |   |-- JwtService.java
|   |   |           |   |-- SecurityConfig.java
|   |   |           |-- item/
|   |   |           |   |-- ItemController.java
|   |   |           |   |-- ItemDto.java
|   |   |           |   |-- ItemEntity.java
|   |   |           |   |-- ItemRepository.java
|   |   |           |   |-- ItemRequest.java
|   |   |           |   |-- ItemService.java
|   |   |           |-- user/
|   |   |           |   |-- ApiResponse.java
|   |   |           |   |-- ChangeUserDetailsRequest.java
|   |   |           |   |-- UserController.java
|   |   |           |   |-- UserDto.java
|   |   |           |   |-- UserEntity.java
|   |   |           |   |-- UserRepository.java
|   |   |           |   |-- UserService.java
|   |   |           |-- order/
|   |   |           |   |-- CreateOrderRequest.java
|   |   |           |   |-- OrderController.java
|   |   |           |   |-- OrderDto.java
|   |   |           |   |-- OrderEntity.java
|   |   |           |   |-- OrderRepository.java
|   |   |           |   |-- OrderService.java
|   |   |           |-- orderitem/
|   |   |           |   |-- ChangeOrderItemStatusRequest.java
|   |   |           |   |-- OrderItemController.java
|   |   |           |   |-- OrderItemDto.java
|   |   |           |   |-- OrderItemEntity.java
|   |   |           |   |-- OrderItemRepository.java
|   |   |           |   |-- OrderItemService.java
|   |   |-- resources/
|   |   |   |-- application.properties
|   |   |   |-- application-dev.properties
|   |   |   |-- application-prod.properties
|   |-- test/
|       |-- java/
|           |-- com/
|               |-- marketplace/
|                   |-- MarketplaceApplicationTests.java
|-- logs/
|   |-- marketplace.log
|-- README.md
```
