package com.PiratesOfTheSiliconValley.LibSys.views.staff;

import com.PiratesOfTheSiliconValley.LibSys.backend.model.Role;
import com.PiratesOfTheSiliconValley.LibSys.backend.model.User;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@SpringBootTest
public class StaffUsersFormTest {

    private List<User> users;
    private User user;

    @Before
    public void setupData(){

        users = new ArrayList<>();
        user = new User();

        user.setPersonal_id_number("195809072345");
        user.setFirstname("Walter");
        user.setLastname("White");
        user.setPhone("0736244054");
        user.setRole(Role.USER);
        user.setUsername("Heisenberg");
        user.setPasswordHash("1234");

        users.add(user);

    }


    @Test
    public void formFieldsPopulated() {
        StaffUsersForm form = new StaffUsersForm(users);
        form.setUser(user);

        String password = DigestUtils.sha1Hex("1234" + user.getPasswordSalt());
        Assert.assertEquals("195809072345", form.personal_id_number.getValue());
        Assert.assertEquals("Walter", form.firstname.getValue());
        Assert.assertEquals("White", form.lastname.getValue());
        Assert.assertEquals("0736244054", form.phone.getValue());
        Assert.assertEquals("USER", form.role.getValue().toString());
        Assert.assertEquals("Heisenberg", form.username.getValue());
        Assert.assertEquals(password, form.passwordHash.getValue());
    }

    @Test
    public void saveCorrectValues() {
        StaffUsersForm form = new StaffUsersForm(users);
        form.setUser(user);

        form.personal_id_number.setValue("198409240987");
        form.firstname.setValue("Jesse");
        form.lastname.setValue("Pinkman");
        form.phone.setValue("0746746234");
        form.role.setValue(Role.USER);
        form.username.setValue("Cap'n Cook");

        form.passwordHash.setValue("2580");

        AtomicReference<User> savedUserRef = new AtomicReference<>(null);
        form.addListener(StaffUsersForm.SaveEvent.class, e -> savedUserRef.set(e.getUser()));
        form.save.click();

        User savedUser = savedUserRef.get();
        String password = DigestUtils.sha1Hex("2580" + savedUser.getPasswordSalt());

        Assert.assertEquals("198409240987", savedUser.getPersonal_id_number());
        Assert.assertEquals("Jesse", savedUser.getFirstname());
        Assert.assertEquals("Pinkman", savedUser.getLastname());
        Assert.assertEquals("0746746234", savedUser.getPhone());
        Assert.assertEquals(Role.USER, savedUser.getRole());
        Assert.assertEquals("Cap'n Cook", savedUser.getUsername());
        Assert.assertEquals(password, savedUser.getPasswordHash());

    }
}