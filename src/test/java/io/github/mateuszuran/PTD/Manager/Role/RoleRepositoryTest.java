package io.github.mateuszuran.PTD.Manager.Role;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class RoleRepositoryTest {
    @Autowired
    RoleRepository roleRepository;

    @Test
    void findRoleByName() {
        String role = "Admin";
        Role findRole = roleRepository.findByName(role);
        assertThat(findRole).isNotNull();
    }
}