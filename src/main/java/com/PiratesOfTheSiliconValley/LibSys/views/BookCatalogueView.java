package com.PiratesOfTheSiliconValley.LibSys.views;

import com.PiratesOfTheSiliconValley.LibSys.backend.controller.BookController;
import com.PiratesOfTheSiliconValley.LibSys.backend.model.Book;
import com.PiratesOfTheSiliconValley.LibSys.backend.repository.BookRepository;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.gridpro.GridPro;
import com.vaadin.flow.component.littemplate.LitTemplate;
import com.vaadin.flow.component.template.Id;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

@Route(value = "/bookcatalogue", layout = Navbar.class)
@PageTitle("Book Catalogue")
@CssImport("./views/bookcatalogue/book-catalogue-view.css")
@Tag("book-catalogue-view")
@JsModule("./views/bookcatalogue/book-catalogue-view.ts")
public class BookCatalogueView extends LitTemplate {


    @Id("mygrid")
    private GridPro<Book> grid;
    private TextField filter = new TextField();
    private BookController bookController;
    private BookRepository bookRepository;

    ListDataProvider <Book> dataProvider;

    private Grid.Column<Book> titleColumn;
    private Grid.Column<Book> authorColumn;
    private Grid.Column<Book> languageColumn;
    private Grid.Column<Book> genre1Column;
    private Grid.Column<Book> genre2Column;
    private Grid.Column<Book> formatColumn;
    private Grid.Column<Book> publishingYearColumn;

    public BookCatalogueView(BookController bookController,
                             BookRepository bookRepository) {
        this.bookController = bookController;
        this.bookRepository = bookRepository;
        createGrid();
    }

    private void createGrid() {
        createGridComponent();
        addColumnsToGrid();
        addFiltersToGrid();

        grid.addItemClickListener(e -> UI.getCurrent()
                                         .navigate(BookView.class, e.getItem()
                                                                    .getBookID()
                                                                    .toString()));
    }

    private void createGridComponent() {
       // grid.setSelectionMode(SelectionMode.MULTI);
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_COLUMN_BORDERS);
        grid.setHeight("100%");

        dataProvider = new ListDataProvider<>(bookController.findAll());
        grid.setDataProvider(dataProvider);
    }

    private void addColumnsToGrid() {
        createTitleColumn();
        createAuthorColumn();
        createLanguageColumn();
        createGenre1Column();
        createGenre2Column();
        createFormatColumn();
        createPublishingYearColumn();
    }

    private void createTitleColumn() {
        titleColumn = grid.addColumn(Book::getTitle, "title").setHeader("TITLE").setWidth("250px").setFlexGrow(0);
    }

    private void createAuthorColumn() {
        authorColumn = grid.addColumn(Book::getAuthor, "author").setHeader("AUTHOR").setWidth("200px").setFlexGrow(0);
    }

    private void createLanguageColumn() {
        languageColumn = grid.addColumn(Book::getLanguage, "language").setHeader("LANGUAGE").setWidth("180px").setFlexGrow(0);
    }

    private void createGenre1Column() {
        genre1Column = grid.addColumn(Book::getGenre1, "genre1").setHeader("GENRE").setWidth("200px").setFlexGrow(0);
    }

    private void createGenre2Column() {
        genre2Column = grid.addColumn(Book::getGenre2, "genre2").setHeader("GENRE").setWidth("200px").setFlexGrow(0);
    }

    private void createFormatColumn() {
        formatColumn = grid.addColumn(Book::getFormat, "format").setHeader("FORMAT").setWidth("150px").setFlexGrow(0);
    }

    private void createPublishingYearColumn() {
        publishingYearColumn = grid.addColumn(Book::getPublishingyear, "publishingyear").setHeader("PUBLISHING YEAR").setWidth("200px").setFlexGrow(0);
    }

    private void addFiltersToGrid() {
        HeaderRow filterRow = grid.appendHeaderRow();

        TextField titleFilter = new TextField();
        titleFilter.setPlaceholder("Filter");
        titleFilter.setClearButtonVisible(true);
        titleFilter.setWidth("100%");
        titleFilter.setValueChangeMode(ValueChangeMode.EAGER);
        titleFilter.addValueChangeListener(event -> dataProvider
                .addFilter(book -> StringUtils.containsIgnoreCase(book.getTitle(), titleFilter.getValue())));
        filterRow.getCell(titleColumn).setComponent(titleFilter);

        TextField authorFilter = new TextField();
        authorFilter.setPlaceholder("Filter");
        authorFilter.setClearButtonVisible(true);
        authorFilter.setWidth("100%");
        authorFilter.setValueChangeMode(ValueChangeMode.EAGER);
        authorFilter.addValueChangeListener(event -> dataProvider
                .addFilter(book -> StringUtils.containsIgnoreCase(book.getAuthor(), authorFilter.getValue())));
        filterRow.getCell(authorColumn).setComponent(authorFilter);


        ComboBox<String> languageFilter = new ComboBox<>();
        languageFilter.setItems(Arrays.asList("Svenska", "English"));
        languageFilter.setPlaceholder("Filter");
        languageFilter.setClearButtonVisible(true);
        languageFilter.setWidth("100%");
        languageFilter.addValueChangeListener(
                event -> dataProvider.addFilter(book -> areLanguageEqual(book, languageFilter)));
        filterRow.getCell(languageColumn).setComponent(languageFilter);

        TextField genre1Filter = new TextField();
        genre1Filter.setPlaceholder("Filter");
        genre1Filter.setClearButtonVisible(true);
        genre1Filter.setWidth("100%");
        genre1Filter.setValueChangeMode(ValueChangeMode.EAGER);
        genre1Filter.addValueChangeListener(event -> dataProvider
                .addFilter(book -> StringUtils.containsIgnoreCase(book.getGenre1(), genre1Filter.getValue())));
        filterRow.getCell(genre1Column).setComponent(genre1Filter);

        TextField genre2Filter = new TextField();
        genre2Filter.setPlaceholder("Filter");
        genre2Filter.setClearButtonVisible(true);
        genre2Filter.setWidth("100%");
        genre2Filter.setValueChangeMode(ValueChangeMode.EAGER);
        genre2Filter.addValueChangeListener(event -> dataProvider
                .addFilter(book -> StringUtils.containsIgnoreCase(book.getGenre2(), genre2Filter.getValue())));
        filterRow.getCell(genre2Column).setComponent(genre2Filter);

        ComboBox<Book.Format> formatFilter = new ComboBox<>();
        formatFilter.setItems(Book.Format.values());
        formatFilter.setPlaceholder("Filter");
        formatFilter.setClearButtonVisible(true);
        formatFilter.setWidth("100%");
        formatFilter.addValueChangeListener(
                event -> dataProvider.addFilter(book -> areFormatEqual(book, formatFilter)));
        filterRow.getCell(formatColumn).setComponent(formatFilter);


        TextField publishingYearFilter = new TextField();
        publishingYearFilter.setPlaceholder("Filter");
        publishingYearFilter.setClearButtonVisible(true);
        publishingYearFilter.setWidth("100%");
        publishingYearFilter.setValueChangeMode(ValueChangeMode.EAGER);
        publishingYearFilter.addValueChangeListener(event -> dataProvider.addFilter(
                book -> StringUtils.containsIgnoreCase(Integer.toString(book.getPublishingyear()), publishingYearFilter.getValue())));
        filterRow.getCell(publishingYearColumn).setComponent(publishingYearFilter);
    }


    private boolean areLanguageEqual(Book book, ComboBox<String> languageFilter) {
        String languageFilterValue = languageFilter.getValue();
        if (languageFilterValue != null) {
            return StringUtils.equals(book.getLanguage(), languageFilterValue);
        }
        return true;
    }

    private boolean areFormatEqual(Book book, ComboBox<Book.Format> formatFilter) {
        Enum formatFilterValue = formatFilter.getValue();
        if (formatFilterValue != null) {
            return StringUtils.equals(book.getFormat(), (CharSequence) formatFilterValue);
        }
        return true;
    }
}
