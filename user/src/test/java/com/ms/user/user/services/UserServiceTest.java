package com.ms.user.user.services;

import com.ms.user.core.exceptions.ApiException;
import com.ms.user.user.dtos.UserCreateRequest;
import com.ms.user.user.models.UserModel;
import com.ms.user.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataIntegrityViolationException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceTest {

    private final UserRepository repository = mock(UserRepository.class);
    private final UserService service = new UserService(repository);

    @Test
    void createNormalizesEmailAndName() {
        when(repository.saveAndFlush(any())).thenAnswer(inv -> inv.getArgument(0));

        var response = service.create(new UserCreateRequest("  Pedro ", " Pedro@Gmail.COM "));

        assertThat(response.name()).isEqualTo("Pedro");
        assertThat(response.email()).isEqualTo("pedro@gmail.com");
        verify(repository).existsByEmail("pedro@gmail.com");
    }

    @Test
    void createRejectsExistingEmail() {
        when(repository.existsByEmail("pedro@gmail.com")).thenReturn(true);

        assertThatThrownBy(() -> service.create(new UserCreateRequest("Pedro", "pedro@gmail.com")))
                .isInstanceOf(ApiException.class)
                .extracting("status").isEqualTo(409);
        verify(repository, never()).saveAndFlush(any());
    }

    @Test
    void createMapsConcurrentDuplicateToConflict() {
        when(repository.saveAndFlush(any(UserModel.class))).thenThrow(new DataIntegrityViolationException("duplicate"));

        assertThatThrownBy(() -> service.create(new UserCreateRequest("Pedro", "pedro@gmail.com")))
                .isInstanceOf(ApiException.class)
                .extracting("status").isEqualTo(409);
    }
}
