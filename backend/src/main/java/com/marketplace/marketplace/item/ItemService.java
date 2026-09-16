package com.marketplace.marketplace.item;

import com.marketplace.marketplace.user.UserEntity;
import com.marketplace.marketplace.user.UserRepository;
import com.marketplace.marketplace.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ItemService {

    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    public ItemService(ItemRepository itemRepository, UserRepository userRepository, UserService userService) {
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    public ItemDto createItem(String userEmail,String name, String description,  BigDecimal price) {
        if(userEmail==null ||userEmail.isBlank()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User does not exist");
        }

        if (name == null || name.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Item name is required");
        }

        if (price == null || price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Price is required and must be a positive value");
        }

        UUID sellerId = userService.getUserByEmail(userEmail).get().getId();

        if (sellerId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Seller id is required");
        }

        UserEntity seller = userRepository.findById(sellerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Seller not found"));

        ItemEntity item = new ItemEntity(name, description, seller, price);
        return ItemDto.fromEntity(itemRepository.save(item));
    }

    public Optional<ItemDto> getItemById(UUID id) {
        return itemRepository.findById(id)
                .map(ItemDto::fromEntity);
    }

    public List<ItemDto> getAllItems() {
        return itemRepository.findAll().stream()
                .map(ItemDto::fromEntity)
                .toList();
    }
}
