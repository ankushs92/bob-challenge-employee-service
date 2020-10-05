package com.takeaway.challenge;

import com.takeaway.challenge.domain.auth.Role;
import com.takeaway.challenge.domain.auth.User;
import com.takeaway.challenge.repository.DepartmentRepository;
import com.takeaway.challenge.repository.EmployeeRepository;
import com.takeaway.challenge.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@SpringBootTest
@AutoConfigureMockMvc
public class ChallengeApplicationTests {

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentRepository departmentRepository;


    protected static final String EMAIL = "root";
    protected static final String PASSWORD = "root";

    protected User user = null;

    @BeforeEach
    public void insertUserAndDeleteAllOtherRepos() {
        userRepository.deleteAll();
        var insertUser = new User();
        var bcrypt = new BCryptPasswordEncoder();
        insertUser.setAccountNonExpired(true);
        insertUser.setAccountNonLocked(true);
        insertUser.setCredentialsNonExpired(true);
        insertUser.setEmail(EMAIL);
        insertUser.setPassword(bcrypt.encode(PASSWORD));
        insertUser.setEnabled(true);
        insertUser.setRole(Role.USER);
        insertUser.setCreated(ZonedDateTime.now(ZoneId.of("UTC")));
        user = userRepository.save(insertUser);

        employeeRepository.deleteAll();
        departmentRepository.deleteAll();
    }
}
