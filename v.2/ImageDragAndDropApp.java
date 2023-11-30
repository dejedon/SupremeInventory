import javax.swing.*;

import javafx.event.ActionEvent;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

public class ImageDragAndDropApp {
    private JFrame frame;
    private JTextField filePathField;
    private JPanel imagePanel;
    private static final int BORDER = 12;  
    private static final int GAP    = 5;  
    GBHelper pos = new GBHelper();
    private String location;
    
    public ImageDragAndDropApp() {
        
    }

    public void ImageDragAndDropApp2() {
        frame = new JFrame("Image Drag and Drop");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setSize(200,200);

        filePathField = new JTextField();
        filePathField.setEditable(false);
        frame.add(filePathField, BorderLayout.NORTH);

        imagePanel = new JPanel();
        frame.add(imagePanel, BorderLayout.CENTER);

        frame.pack();
        frame.setVisible(true);

        addDropTarget(imagePanel);
        

    }

    private void addDropTarget(Component component) {
        new DropTarget(component, DnDConstants.ACTION_COPY, new DropTargetAdapter() {
            @Override
            public void drop(DropTargetDropEvent dtde) {
                try {
                    Transferable transferable = dtde.getTransferable();

                    if (transferable.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
                        dtde.acceptDrop(DnDConstants.ACTION_COPY);
                        List<File> fileList = (List<File>) transferable.getTransferData(DataFlavor.javaFileListFlavor);

                        if (!fileList.isEmpty()) {
                            File file = fileList.get(0);
                            filePathField.setText(file.getAbsolutePath());
                            location = file.getAbsolutePath();

                            // Display the image in the imagePanel
                            ImageIcon imageIcon = new ImageIcon(file.getAbsolutePath());
                            JLabel imageLabel = new JLabel(imageIcon);
                            imagePanel.removeAll();
                            imagePanel.add(imageLabel);
                            imagePanel.revalidate();
                            imagePanel.repaint();
                        }

                        dtde.dropComplete(true);
                    } else {
                        dtde.rejectDrop();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    dtde.rejectDrop();
                }
            }
        });
    }

    public String getLocation(){
        frame.dispose();
        return location;
        
    }

   



    //public static void main(String[] args) {
    //    SwingUtilities.invokeLater(() -> new ImageDragAndDropApp());
    //}
}
