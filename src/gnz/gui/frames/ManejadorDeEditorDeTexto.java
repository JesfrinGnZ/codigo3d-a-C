package gnz.gui.frames;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.undo.UndoManager;

/**
 *
 * @author jesfrin
 */
public class ManejadorDeEditorDeTexto {

    private boolean documentoEstaGuardado;
    private String nombreDeDocumento;
    private File rutaDeArchivo;
    private final JTextArea text;
    private final UndoManager editManager;
    private final EditorDeTextoFrame frame;

    public ManejadorDeEditorDeTexto(JTextArea text, UndoManager editManager, EditorDeTextoFrame frame) {
        this.documentoEstaGuardado = false;
        this.text = text;
        this.editManager = editManager;
        this.frame = frame;
        this.nombreDeDocumento = null;
        this.rutaDeArchivo = null;
    }

    public void copiar() {
        this.text.copy();
    }

    public void pegar() {
        this.text.paste();
    }

    public void cortar() {
        this.text.cut();
    }

    public void rehacer() {
        if (editManager.canRedo()) {
            editManager.redo();
        }
    }

    public void deshacer() {
        if (editManager.canUndo()) {
            editManager.undo();
        }
    }

    public void crearNuevoArchivo() throws Exception {
        if (verificarGuardadoYSeguimientoDeAccion()) {
            this.text.setText("");
            this.nombreDeDocumento = null;
            this.documentoEstaGuardado = false;
        }
    }

    public void abrirArchivo() throws Exception {
        if (verificarGuardadoYSeguimientoDeAccion()) {
            JFileChooser file = new JFileChooser();
            file.showOpenDialog(frame);
            this.rutaDeArchivo = file.getSelectedFile();
            String texto = ManejadorDeArchivos.leerArchivo(rutaDeArchivo);
            this.text.setText(texto);
        }
    }

    public File guardarComo() throws Exception {
        JFileChooser chooser = new JFileChooser();
        chooser.showSaveDialog(frame);
        File ruta = chooser.getSelectedFile();
        ManejadorDeArchivos.escribirArchivo(ruta, this.text.getText());
        JOptionPane.showMessageDialog(frame, "Archivo guardado con exito");
        return ruta;
    }

    public void guardarArchivo() throws Exception {
        if (rutaDeArchivo == null) {
            this.rutaDeArchivo=guardarComo();
        } else {
            ManejadorDeArchivos.escribirArchivo(rutaDeArchivo, this.text.getText());
            JOptionPane.showMessageDialog(frame, "Archivo guardado con exito");
        }
    }

    public boolean isDocumentoEstaGuardado() {
        return documentoEstaGuardado;
    }

    public void setDocumentoEstaGuardado(boolean documentoEstaGuardado) {
        this.documentoEstaGuardado = documentoEstaGuardado;
    }

    public String getNombreDeDocumento() {
        return nombreDeDocumento;
    }

    public void setNombreDeDocumento(String nombreDeDocumento) {
        this.nombreDeDocumento = nombreDeDocumento;
    }

    public boolean verificarGuardadoYSeguimientoDeAccion() throws Exception {
        if (documentoEstaGuardado) {
            JOptionPane.showMessageDialog(frame, "El documento ya ha sido guardado");
        } else {
            int opcion = JOptionPane.showConfirmDialog(frame, "Desea guardar el archivo actual?");
            switch (opcion) {
                //Case -1 cerro la ventana, 1 no lo desea guardar
                case -1:
                    return false;
                case 0://Acepta guardar el archivo
                    guardarArchivo();
                    break;
                case 2://Cancelo la opcion
                    return false;
            }
        }
        return true;
    }
    
    

}
