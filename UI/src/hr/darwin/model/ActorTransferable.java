/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.darwin.model;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

/**
 *
 * @author Marko
 */
public class ActorTransferable implements Transferable {

    public static final DataFlavor PERSON_FLAVOR = new DataFlavor(Actor.class, "Actor");
    public static final DataFlavor[] SUPPORTED_FLAVORS = {PERSON_FLAVOR};
    private final Actor actor;

    public ActorTransferable(Actor actor) {
        this.actor = actor;
    }
    
    @Override
    public DataFlavor[] getTransferDataFlavors() {
        return SUPPORTED_FLAVORS;
    }

    @Override
    public boolean isDataFlavorSupported(DataFlavor flavor) {
        return flavor.equals(PERSON_FLAVOR);
    }

    @Override
    public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
        if (flavor.equals(PERSON_FLAVOR)) {
            return actor;
        }
        throw new UnsupportedFlavorException(flavor);
    }
    
}
