package com.marketplace.marketplace.user;

import java.util.Optional;

public record ChangeUserDetailsRequest(Optional<String> firstName,Optional<String>lastName,Optional<String>email,Optional<String>phoneNumber) {

}
