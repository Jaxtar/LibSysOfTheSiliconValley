package com.PiratesOfTheSiliconValley.LibSys.views.publicpages;

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

@Route(value = "/bookcatalogue", layout = Navbar.class)
@PageTitle("Böcker")
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
                                                                    .getIsbn()));
    }

    private void createGridComponent() {
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_COLUMN_BORDERS);
        grid.setHeight("100%");

        dataProvider = new ListDataProvider<>(bookController.findAll());
        grid.setDataProvider(dataProvider);
    }

    private void addColumnsToGrid() {
        createTitleColumn();
        createAuthorColumn();
        createLanguageColumn();
        createFormatColumn();
        createGenre1Column();
        createGenre2Column();
        createPublishingYearColumn();
    }

    private void createTitleColumn() {
        titleColumn = grid.addColumn(Book::getTitle, "title").setHeader("Titel").setWidth("330px").setFlexGrow(0);
    }

    private void createAuthorColumn() {
        authorColumn = grid.addColumn(Book::getAuthor, "author").setHeader("Författare").setWidth("250px").setFlexGrow(0);
    }

    private void createLanguageColumn() {
        languageColumn = grid.addColumn(Book::getLanguage, "language").setHeader("Språk").setWidth("180px").setFlexGrow(0);
    }

    private void createFormatColumn() {
        formatColumn = grid.addColumn(Book::getFormat, "format").setHeader("Format").setWidth("180px").setFlexGrow(0);
    }

    private void createGenre1Column() {
        genre1Column = grid.addColumn(Book::getGenre1, "genre1").setHeader("Genre").setWidth("250px").setFlexGrow(0);
    }

    private void createGenre2Column() {
        genre2Column = grid.addColumn(Book::getGenre2, "genre2").setHeader("Genre").setWidth("250px").setFlexGrow(0);
    }

    private void createPublishingYearColumn() {
        publishingYearColumn = grid.addColumn(Book::getPublishingyear, "publishingyear").setHeader("Utgivningsår").setWidth("200px").setFlexGrow(0);
    }

    private void addFiltersToGrid() {
        HeaderRow filterRow = grid.appendHeaderRow();

        TextField titleFilter = new TextField();
        titleFilter.setPlaceholder("Sök titel");
        titleFilter.setClearButtonVisible(true);
        titleFilter.setWidth("100%");
        titleFilter.setValueChangeMode(ValueChangeMode.EAGER);
        titleFilter.addValueChangeListener(event -> dataProvider
                .addFilter(book -> StringUtils.containsIgnoreCase(book.getTitle(), titleFilter.getValue())));
        filterRow.getCell(titleColumn).setComponent(titleFilter);

        TextField authorFilter = new TextField();
        authorFilter.setPlaceholder("Sök författare");
        authorFilter.setClearButtonVisible(true);
        authorFilter.setWidth("100%");
        authorFilter.setValueChangeMode(ValueChangeMode.EAGER);
        authorFilter.addValueChangeListener(event -> dataProvider
                .addFilter(book -> StringUtils.containsIgnoreCase(book.getAuthor(), authorFilter.getValue())));
        filterRow.getCell(authorColumn).setComponent(authorFilter);


        ComboBox<Book.Language> languageFilter = new ComboBox<>();
        languageFilter.setItems(Book.Language.values());
        languageFilter.setPlaceholder("Sök språk");
        languageFilter.setClearButtonVisible(true);
        languageFilter.setWidth("100%");
        languageFilter.addValueChangeListener(
                event -> dataProvider.addFilter(book -> areLanguageEqual(book, languageFilter)));
        filterRow.getCell(languageColumn).setComponent(languageFilter);

        ComboBox<Book.Genre> genre1Filter = new ComboBox<>();
        genre1Filter.setItems(Book.Genre.values());
        genre1Filter.setPlaceholder("Sök genre");
        genre1Filter.setClearButtonVisible(true);
        genre1Filter.setWidth("100%");
        genre1Filter.addValueChangeListener(
            event -> dataProvider.addFilter(book -> areGenre1Equal(book, genre1Filter)));
        filterRow.getCell(genre1Column).setComponent(genre1Filter);

        ComboBox<Book.Genre> genre2Filter = new ComboBox<>();
        genre2Filter.setItems(Book.Genre.values());
        genre2Filter.setPlaceholder("Sök genre");
        genre2Filter.setClearButtonVisible(true);
        genre2Filter.setWidth("100%");
        genre2Filter.addValueChangeListener(
                event -> dataProvider.addFilter(book -> areGenre2Equal(book, genre2Filter)));
        filterRow.getCell(genre2Column).setComponent(genre2Filter);

        ComboBox<Book.Format> formatFilter = new ComboBox<>();
        formatFilter.setItems(Book.Format.values());
        formatFilter.setPlaceholder("Sök format");
        formatFilter.setClearButtonVisible(true);
        formatFilter.setWidth("100%");
        formatFilter.addValueChangeListener(
                event -> dataProvider.addFilter(book -> areFormatEqual(book, formatFilter)));
        filterRow.getCell(formatColumn).setComponent(formatFilter);


        TextField publishingYearFilter = new TextField();
        publishingYearFilter.setPlaceholder("Sök år");
        publishingYearFilter.setClearButtonVisible(true);
        publishingYearFilter.setWidth("100%");
        publishingYearFilter.setValueChangeMode(ValueChangeMode.EAGER);
        publishingYearFilter.addValueChangeListener(event -> dataProvider.addFilter(
                book -> StringUtils.containsIgnoreCase(Integer.toString(book.getPublishingyear()), publishingYearFilter.getValue())));
        filterRow.getCell(publishingYearColumn).setComponent(publishingYearFilter);
    }


    private boolean areLanguageEqual(Book book, ComboBox<Book.Language> languageFilter) {
        if (languageFilter.getValue() != null) {
            return book.getLanguage().toString().equals(languageFilter.getValue().toString());
        }
        return true;
    }

    private boolean areFormatEqual(Book book, ComboBox<Book.Format> formatFilter) {
        if (formatFilter.getValue() != null) {
            return book.getFormat().toString().equals(formatFilter.getValue().toString());
        }
        return true;
    }

    private boolean areGenre1Equal(Book book, ComboBox<Book.Genre> formatFilter) {
        if (formatFilter.getValue() != null) {
            return book.getGenre1().toString().equals(formatFilter.getValue().toString());
        }
        return true;
    }

    private boolean areGenre2Equal(Book book, ComboBox<Book.Genre> formatFilter) {
        if (formatFilter.getValue() != null) {
            return book.getGenre2().toString().equals(formatFilter.getValue().toString());
        }
        return true;
    }
}
