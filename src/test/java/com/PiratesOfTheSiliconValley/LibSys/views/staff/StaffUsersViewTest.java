package com.PiratesOfTheSiliconValley.LibSys.views.staff;

import com.PiratesOfTheSiliconValley.LibSys.backend.model.User;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.data.provider.ListDataProvider;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StaffUsersViewTest {

    @Autowired
    private StaffUsersView staffUsersView;

    @Test
    public void formShownWhenContactSelected(){
        Grid<User> grid = staffUsersView.grid;
        User firstUser = getFirstItem(grid);

        StaffUsersForm form = staffUsersView.userForm;

        Assert.assertFalse(form.isVisible());
        grid.asSingleSelect().setValue(firstUser);
        Assert.assertTrue(form.isVisible());
        Assert.assertEquals(firstUser.getFirstname(), form.firstname.getValue());
        Assert.assertEquals(firstUser.getLastname(), form.lastname.getValue());

    }

    private User getFirstItem(Grid<User> grid){
        return ((ListDataProvider<User>) grid.getDataProvider()).getItems().iterator().next();
    }

}
