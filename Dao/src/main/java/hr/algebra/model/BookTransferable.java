/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.model;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

/**
 *
 * @author AdrianBusak
 */
public class BookTransferable implements Transferable {

    public static final DataFlavor BOOK_FLAVOR = new DataFlavor(Book.class, "Book");
    private static final DataFlavor[] SUPPORTED_FLAVORS = {BOOK_FLAVOR};

    private final Book book;

    public BookTransferable(Book book) {
        this.book = book;
    }

    @Override
    public DataFlavor[] getTransferDataFlavors() {
        return SUPPORTED_FLAVORS;
    }

    @Override
    public boolean isDataFlavorSupported(DataFlavor flavor) {
        return BOOK_FLAVOR.equals(flavor);
    }

    @Override
    public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
        if (BOOK_FLAVOR.equals(flavor)) {
            return book;
        } else {
            throw new UnsupportedFlavorException(flavor);
        }
    }
}
