package simple_gui_app;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.*;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Simple GUI that saves HashMap to a file and reads/updates the data from the file.
 * Date: April 15, 2021
 * Programmed by: Tommy Chiu
 */

public class simple_gui extends Application
{

// Nodes
    
    GridPane  pane           = new GridPane ();
    Label     idLabel        = new Label    ("ID: ");
    Label     gpaLabel       = new Label    ("PRICE: ");
    CheckBox  editCBox       = new CheckBox ("Edit");
    TextField idTextField    = new TextField();
    TextField priceTextField = new TextField();
    Button    deleteButton   = new Button   ("Delete Product");
    Button    updateButton   = new Button   ("Update Database");
    Button    addButton      = new Button   ("Add/Modify");
    Button    saveButton     = new Button   ("Save Database");

// Product object
    
    Product airPods = new Product("AirPods"             , 1, 239.99);
    Product ducky   = new Product("Ducky Mecha Keyboard", 2, 199.89);
    Product yeti    = new Product("Blue Yeti Mic"       , 3, 129.99);

// ComboBox / HashMap
    
    ComboBox<String>          comboBox = new ComboBox<>();
    HashMap <String, Product> products = new HashMap<>();
    
    @Override
    public void start(Stage stage) throws IOException, ClassNotFoundException
    {
                
    // HashMap 
        
        products.put(airPods.name, airPods);
        products.put(ducky  .name, ducky);
        products.put(yeti   .name, yeti);
    
    // ComboBox
    
        comboBox.getItems().add((airPods.name));
        comboBox.getItems().add((ducky  .name));
        comboBox.getItems().add((yeti   .name));
        
    // Dimensions of nodes
        
        comboBox      .setPrefWidth(150);
        
        editCBox      .setPrefWidth(50);
        
        idLabel       .setPrefWidth(50);
        gpaLabel      .setPrefWidth(50);
        
        idTextField   .setPrefWidth(150);
        priceTextField.setPrefWidth(50);
        
        deleteButton  .setPrefWidth(150);
        updateButton  .setPrefWidth(150);
        addButton     .setPrefWidth(150);
        saveButton    .setPrefWidth(150);
    
        pane          .setPadding  (new Insets(20,20,20,20));
        pane          .setHgap     (20);
        pane          .setVgap     (20);
        pane          .getChildren ().addAll(comboBox, editCBox, idLabel, idTextField, gpaLabel, priceTextField, deleteButton, updateButton, addButton, saveButton);
        
    // Position of nodes
    
        pane.setRowIndex   (comboBox, 0);
        pane.setColumnIndex(comboBox, 0);
        
        pane.setRowIndex   (editCBox, 2);
        pane.setColumnIndex(editCBox, 0);
        
        pane.setRowIndex   (idLabel, 0);
        pane.setColumnIndex(idLabel, 2);
        
        pane.setRowIndex   (idTextField, 0);
        pane.setColumnIndex(idTextField, 3);
        
        pane.setRowIndex   (gpaLabel, 1);
        pane.setColumnIndex(gpaLabel, 2);
        
        pane.setRowIndex   (priceTextField, 1);
        pane.setColumnIndex(priceTextField, 3);
        
        pane.setRowIndex   (deleteButton, 3);
        pane.setColumnIndex(deleteButton, 0);
        
        pane.setRowIndex   (updateButton, 3);
        pane.setColumnIndex(updateButton, 1); 
        
        pane.setRowIndex   (addButton, 3);
        pane.setColumnIndex(addButton, 2);
        
        pane.setRowIndex   (saveButton, 3);
        pane.setColumnIndex(saveButton, 3);
        
    // Lambda Expressions
    
        comboBox.setOnAction(e ->
        {
            String selectedProduct = comboBox.getValue();

            try
            {
                idTextField   .setText(String.valueOf(products.get(selectedProduct).id));
                priceTextField.setText(String.valueOf(products.get(selectedProduct).price));
            }
            catch (NullPointerException npe)
            {
                System.out.println("NullPointerException Thrown.");
            }

            if(selectedProduct == null)
            {
                return;
            }
            
            if(products.get(selectedProduct) == null)
            {
                return;
            }   
        });
        
        deleteButton.setOnAction(e ->
        {
            String selectedProduct = comboBox.getValue();
            
            products      .remove(selectedProduct);
            comboBox      .getItems().remove(selectedProduct);
            idTextField   .setText("");
            priceTextField.setText("");
        });
        
        addButton.setOnAction(e ->
        {
            String  selectedProduct = comboBox.getValue();

            try
            {
                Product addProduct = new Product(selectedProduct, Integer.valueOf(idTextField.getText()), Double.valueOf(priceTextField.getText()));

                products.put(selectedProduct, addProduct);

                if (comboBox.getItems().contains(selectedProduct) == false)
                {
                    comboBox.getItems().add(selectedProduct);
                }
            }
            catch(NumberFormatException nfe)
            {
                System.out.println("NumberFormatException Thrown.");
            }
            

        });
        
        saveButton.setOnAction(e ->
        {
            try
            {
                saveProductsIntoFile();
            }
            catch (IOException ex)
            {
                Logger.getLogger(simple_gui.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        updateButton.setOnAction(e ->
        {
            try
            {
                readProductsFromFile();
            }
            catch (IOException ex)
            {
                Logger.getLogger(simple_gui.class.getName()).log(Level.SEVERE, null, ex);
            }
            catch (ClassNotFoundException ex)
            {
                Logger.getLogger(simple_gui.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        editCBox.setOnAction(e ->
        {
            if(editCBox.isSelected())
            {
                enableAll();
            }
            else
            {
                disableAll();
            }
        });
    
    // Scene
    
        Scene scene = new Scene(pane, 700, 200);
        stage.setScene(scene);
        stage.show();
    }
    
// Methods
    
    public void disableAll()
    {
        comboBox      .setEditable (false);
        idTextField   .setDisable  (true);
        priceTextField.setDisable  (true);
        deleteButton  .setDisable  (true);
        updateButton  .setDisable  (true);
        addButton     .setDisable  (true);
        saveButton    .setDisable  (true);
    }
    
    public void enableAll()
    {
        comboBox      .setEditable (true);
        idTextField   .setDisable  (false);
        priceTextField.setDisable  (false);
        deleteButton  .setDisable  (false);
        updateButton  .setDisable  (false);
        addButton     .setDisable  (false);
        saveButton    .setDisable  (false);
    }
    
    public void readProductsFromFile() throws IOException, ClassNotFoundException
    {
        comboBox.getItems().addAll(products.keySet());
        
        File              md     = new File("products.obj");
        FileInputStream   stream = new FileInputStream(md);
        ObjectInputStream input  = new ObjectInputStream(stream);
        
        products = (HashMap)(input.readObject());
        
        for(Object product: products.keySet())
        {
            String name = (String) product;
            int    id   = (Integer)products.get(name).id;
            double gpa  = (Double) products.get(name).price;
            
            String selectedName = comboBox.getValue();
            
            comboBox      .getItems ().add((name));
            idTextField   .setText  (String.valueOf(id));
            priceTextField.setText  (String.valueOf(gpa));
        }
        input.close();
        stream.close();
    }
    
    public void saveProductsIntoFile() throws IOException
    {
        File               md     = new File("products.obj");
        FileOutputStream   stream = new FileOutputStream(md);
        ObjectOutputStream output = new ObjectOutputStream(stream);
        
        output.writeObject(products);
        
        output.close();
        stream.close();
    }
    
    public static void main(String[] args)
    {  
        launch(args);  
    }
}
