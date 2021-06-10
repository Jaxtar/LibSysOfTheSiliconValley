package com.PiratesOfTheSiliconValley.LibSys.views.staff;

import com.PiratesOfTheSiliconValley.LibSys.backend.model.Book;
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
public class StaffBookViewTest {

    @Autowired
    private StaffBookView staffBookView;

    @Test
    public void formShownWhenContactSelected(){
        Grid<Book> grid = staffBookView.grid;
        Book firstBook = getFirstItem(grid);

        StaffBookForm form = staffBookView.staffBookForm;

        Assert.assertFalse(form.isVisible());
        grid.asSingleSelect().setValue(firstBook);
        Assert.assertTrue(form.isVisible());
        Assert.assertEquals(firstBook.getIsbn(), form.isbn.getValue());
        Assert.assertEquals(firstBook.getTitle(), form.title.getValue());
    }

    private Book getFirstItem(Grid<Book> grid){
        return ((ListDataProvider<Book>) grid.getDataProvider()).getItems().iterator().next();
    }
}
