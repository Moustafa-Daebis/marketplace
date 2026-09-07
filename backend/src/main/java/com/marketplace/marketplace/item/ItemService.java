package com.marketplace.marketplace.item;

import com.marketplace.marketplace.user.UserEntity;
import com.marketplace.marketplace.user.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ItemService {

    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    public ItemService(ItemRepository itemRepository, UserRepository userRepository) {
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
    }

    public ItemDto createItem(String name, String description, UUID sellerId) {
        if (name == null || name.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Item name is required");
        }

        if (sellerId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Seller id is required");
        }

        UserEntity seller = userRepository.findById(sellerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Seller not found"));

        ItemEntity item = new ItemEntity(name, description, seller);
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
