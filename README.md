Marketplace
=========

A simple Spring Boot marketplace web application providing user authentication, item listing, cart management, and basic views. Built with Java and Maven; includes Thymeleaf templates and SQL schema files for H2 and PostgreSQL.

# Project structure (top-level)
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
|   |   |           |   |-- UserController.java
|   |   |           |   |-- UserDto.java
|   |   |           |   |-- UserEntity.java
|   |   |           |   |-- UserRepository.java
|   |   |           |   |-- UserService.java
|   |   |           |-- view/
|   |   |               |-- ViewController.java
|   |   |-- resources/
|   |   |   |-- application.properties
|   |   |   |-- application-dev.properties
|   |   |   |-- application-prod.properties
|   |   |   |-- templates/
|   |   |       |-- add-item.html
|   |   |       |-- index.html
|   |   |       |-- items.html
|   |   |       |-- login.html
|   |   |       |-- register.html
|   |-- test/
|       |-- java/
|           |-- com/
|               |-- marketplace/
|                   |-- MarketplaceApplicationTests.java
|-- logs/
|   |-- marketplace.log
```
