package gnz.gui.frames;

import gnz.backend.analizadores.AnalizadorLexicoCodigo;
import gnz.backend.analizadores.parser;
import gnz.backend.errores.ManejadorDeErrores;
import gnz.backend.manejadorC.ManejadorC;
import gnz.backend.tablas.ManejadorDeTablas;
import java.io.BufferedReader;
import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.BadLocationException;
import javax.swing.undo.UndoManager;

/**
 *
 * @author jesfrin
 */
public class EditorDeTextoFrame extends javax.swing.JFrame {

    private final UndoManager editManager;
    private final ManejadorDeEditorDeTexto manejadorEditor;
    private ManejadorDeTablas manTablas;
    private CodigoCJDialog frameC;

    /**
     * Creates new form EditorDeTextoFrame
     */
    public EditorDeTextoFrame() {
        editManager = new UndoManager();
        initComponents();
        crearOyenteDeEdicion();
        frameC = new CodigoCJDialog(this,false);
        this.manejadorEditor = new ManejadorDeEditorDeTexto(codigoTextArea, editManager, this);
        //Para lineas de codigo
        TextLineNumber lineNumber = new TextLineNumber(codigoTextArea);
        codigojScrollPane.setRowHeaderView(lineNumber);
        //Para lineas de error
        TextLineNumber lineNumber2 = new TextLineNumber(erroresTextArea);
        erroresScrollPane.setRowHeaderView(lineNumber2);
        //Para lineas de 
        TextLineNumber linerNumber3 = new TextLineNumber(codigo3dTextArea);
        codigoTresDireccionesjScrollPane.setRowHeaderView(linerNumber3);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        codigojScrollPane = new javax.swing.JScrollPane();
        codigoTextArea = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        codigoTresDireccionesjScrollPane = new javax.swing.JScrollPane();
        codigo3dTextArea = new javax.swing.JTextArea();
        filaColumnaLabel = new javax.swing.JLabel();
        jToolBar1 = new javax.swing.JToolBar();
        analizarCodigoButton = new javax.swing.JButton();
        limpiarCodigo3dYErroresButton = new javax.swing.JButton();
        limpiarAreaDeTextoButton = new javax.swing.JButton();
        guardar3dButton = new javax.swing.JButton();
        erroresScrollPane = new javax.swing.JScrollPane();
        erroresTextArea = new javax.swing.JTextArea();
        jLabel3 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        nuevoArchivoMenuItem = new javax.swing.JMenuItem();
        abrirArchivoMenuItem2 = new javax.swing.JMenuItem();
        guardarArchivoMenuItem3 = new javax.swing.JMenuItem();
        guardarComoMenuItem4 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        copiarMenuItem = new javax.swing.JMenuItem();
        pegarMenuItem = new javax.swing.JMenuItem();
        cortarMenuItem = new javax.swing.JMenuItem();
        deshacerMenuItem = new javax.swing.JMenuItem();
        rehacerMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        codigoTextArea.setColumns(20);
        codigoTextArea.setRows(5);
        codigoTextArea.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                codigoTextAreaCaretUpdate(evt);
            }
        });
        codigojScrollPane.setViewportView(codigoTextArea);

        jLabel1.setText("Codigo");

        jLabel2.setText("Codigo tres direcciones");

        codigo3dTextArea.setColumns(20);
        codigo3dTextArea.setRows(5);
        codigoTresDireccionesjScrollPane.setViewportView(codigo3dTextArea);

        filaColumnaLabel.setFont(new java.awt.Font("Manjari Bold", 0, 8)); // NOI18N
        filaColumnaLabel.setText("1:1");

        jToolBar1.setRollover(true);

        analizarCodigoButton.setText("Analizar");
        analizarCodigoButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                analizarCodigoButtonActionPerformed(evt);
            }
        });
        jToolBar1.add(analizarCodigoButton);

        limpiarCodigo3dYErroresButton.setText("Limpiar Codigo 3D");
        limpiarCodigo3dYErroresButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                limpiarCodigo3dYErroresButtonActionPerformed(evt);
            }
        });
        jToolBar1.add(limpiarCodigo3dYErroresButton);

        limpiarAreaDeTextoButton.setText("Limpiar Todo");
        limpiarAreaDeTextoButton.setFocusable(false);
        limpiarAreaDeTextoButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        limpiarAreaDeTextoButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        limpiarAreaDeTextoButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                limpiarAreaDeTextoButtonActionPerformed(evt);
            }
        });
        jToolBar1.add(limpiarAreaDeTextoButton);

        guardar3dButton.setText("Guardar 3D");
        guardar3dButton.setFocusable(false);
        guardar3dButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        guardar3dButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(guardar3dButton);

        erroresTextArea.setColumns(20);
        erroresTextArea.setRows(5);
        erroresScrollPane.setViewportView(erroresTextArea);

        jLabel3.setText("Errores");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(172, 172, 172)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(135, 135, 135))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(filaColumnaLabel)
                    .addComponent(codigojScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 419, Short.MAX_VALUE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(200, 200, 200)
                        .addComponent(jLabel3))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(erroresScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 439, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 392, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(7, 7, 7)
                .addComponent(codigoTresDireccionesjScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 419, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(codigoTresDireccionesjScrollPane)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(erroresScrollPane))
                            .addComponent(codigojScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 528, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 17, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(filaColumnaLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jMenu1.setText("Archivo");

        nuevoArchivoMenuItem.setText("Nuevo");
        nuevoArchivoMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nuevoArchivoMenuItemActionPerformed(evt);
            }
        });
        jMenu1.add(nuevoArchivoMenuItem);

        abrirArchivoMenuItem2.setText("Abrir Archivo");
        abrirArchivoMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                abrirArchivoMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(abrirArchivoMenuItem2);

        guardarArchivoMenuItem3.setText("Guardar");
        guardarArchivoMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                guardarArchivoMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(guardarArchivoMenuItem3);

        guardarComoMenuItem4.setText("Guardar Como");
        guardarComoMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                guardarComoMenuItem4ActionPerformed(evt);
            }
        });
        jMenu1.add(guardarComoMenuItem4);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edicion");

        copiarMenuItem.setText("Copiar");
        copiarMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                copiarMenuItemActionPerformed(evt);
            }
        });
        jMenu2.add(copiarMenuItem);

        pegarMenuItem.setText("Pegar");
        pegarMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pegarMenuItemActionPerformed(evt);
            }
        });
        jMenu2.add(pegarMenuItem);

        cortarMenuItem.setText("Cortar");
        cortarMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cortarMenuItemActionPerformed(evt);
            }
        });
        jMenu2.add(cortarMenuItem);

        deshacerMenuItem.setText("Deshacer");
        deshacerMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deshacerMenuItemActionPerformed(evt);
            }
        });
        jMenu2.add(deshacerMenuItem);

        rehacerMenuItem.setText("Rehacer");
        rehacerMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rehacerMenuItemActionPerformed(evt);
            }
        });
        jMenu2.add(rehacerMenuItem);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void copiarMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_copiarMenuItemActionPerformed
        manejadorEditor.copiar();
    }//GEN-LAST:event_copiarMenuItemActionPerformed

    private void pegarMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pegarMenuItemActionPerformed
        manejadorEditor.pegar();
    }//GEN-LAST:event_pegarMenuItemActionPerformed

    private void cortarMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cortarMenuItemActionPerformed
        manejadorEditor.cortar();
    }//GEN-LAST:event_cortarMenuItemActionPerformed

    private void deshacerMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deshacerMenuItemActionPerformed
        manejadorEditor.deshacer();
    }//GEN-LAST:event_deshacerMenuItemActionPerformed

    private void rehacerMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rehacerMenuItemActionPerformed
        manejadorEditor.rehacer();
    }//GEN-LAST:event_rehacerMenuItemActionPerformed

    private void nuevoArchivoMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nuevoArchivoMenuItemActionPerformed
        try {
            manejadorEditor.crearNuevoArchivo();
        } catch (Exception ex) {
            //Logger.getLogger(EditorDeTextoFrame.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Existio un error al crear un nuevo archivo");
        }
    }//GEN-LAST:event_nuevoArchivoMenuItemActionPerformed

    private void abrirArchivoMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_abrirArchivoMenuItem2ActionPerformed
        try {
            manejadorEditor.abrirArchivo();
        } catch (Exception ex) {
            //Logger.getLogger(EditorDeTextoFrame.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Existio un error al abrir archivo");
        }
    }//GEN-LAST:event_abrirArchivoMenuItem2ActionPerformed

    private void guardarArchivoMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_guardarArchivoMenuItem3ActionPerformed
        try {
            manejadorEditor.guardarArchivo();
        } catch (Exception ex) {
            // Logger.getLogger(EditorDeTextoFrame.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Existio un error al guardar archivo");
        }
    }//GEN-LAST:event_guardarArchivoMenuItem3ActionPerformed

    private void guardarComoMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_guardarComoMenuItem4ActionPerformed
        try {
            manejadorEditor.guardarComo();
        } catch (Exception ex) {
            Logger.getLogger(EditorDeTextoFrame.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Existio un error al guardar archivo");
        }
    }//GEN-LAST:event_guardarComoMenuItem4ActionPerformed

    private void codigoTextAreaCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_codigoTextAreaCaretUpdate
        int pos = evt.getDot(); //mira los pasos del puntero
        try {
            int linea = codigoTextArea.getLineOfOffset(pos) + 1; //devuelve la fila
            int columna = pos - codigoTextArea.getLineStartOffset(linea - 1) + 1; //devuelve la columna
            this.filaColumnaLabel.setText(linea + ":" + columna);

        } catch (BadLocationException exc) {
        }
    }//GEN-LAST:event_codigoTextAreaCaretUpdate

    private void analizarCodigoButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_analizarCodigoButtonActionPerformed
        try {
            this.codigo3dTextArea.setText("");
            this.erroresTextArea.setText("");
            this.manTablas = new ManejadorDeTablas(this);
            analizarCodigo(this.codigoTextArea.getText());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Existio un error al analizar el codigo :/");
            Logger.getLogger(EditorDeTextoFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_analizarCodigoButtonActionPerformed

    private void limpiarAreaDeTextoButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_limpiarAreaDeTextoButtonActionPerformed
        this.codigoTextArea.setText("");
        this.codigo3dTextArea.setText("");
        this.erroresTextArea.setText("");
    }//GEN-LAST:event_limpiarAreaDeTextoButtonActionPerformed

    private void limpiarCodigo3dYErroresButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_limpiarCodigo3dYErroresButtonActionPerformed
        this.codigo3dTextArea.setText("");
        this.erroresTextArea.setText("");
    }//GEN-LAST:event_limpiarCodigo3dYErroresButtonActionPerformed
    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem abrirArchivoMenuItem2;
    private javax.swing.JButton analizarCodigoButton;
    private javax.swing.JTextArea codigo3dTextArea;
    private javax.swing.JTextArea codigoTextArea;
    private javax.swing.JScrollPane codigoTresDireccionesjScrollPane;
    private javax.swing.JScrollPane codigojScrollPane;
    private javax.swing.JMenuItem copiarMenuItem;
    private javax.swing.JMenuItem cortarMenuItem;
    private javax.swing.JMenuItem deshacerMenuItem;
    private javax.swing.JScrollPane erroresScrollPane;
    private javax.swing.JTextArea erroresTextArea;
    private javax.swing.JLabel filaColumnaLabel;
    private javax.swing.JButton guardar3dButton;
    private javax.swing.JMenuItem guardarArchivoMenuItem3;
    private javax.swing.JMenuItem guardarComoMenuItem4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JButton limpiarAreaDeTextoButton;
    private javax.swing.JButton limpiarCodigo3dYErroresButton;
    private javax.swing.JMenuItem nuevoArchivoMenuItem;
    private javax.swing.JMenuItem pegarMenuItem;
    private javax.swing.JMenuItem rehacerMenuItem;
    // End of variables declaration//GEN-END:variables

    public void crearOyenteDeEdicion() {
        codigoTextArea.getDocument().addUndoableEditListener(
                new UndoableEditListener() {
            public void undoableEditHappened(UndoableEditEvent e) {
                editManager.addEdit(e.getEdit());
            }
        });
    }

    public void analizarCodigo(String codigo) throws Exception {
        AnalizadorLexicoCodigo lex = new AnalizadorLexicoCodigo(new BufferedReader(new StringReader(codigo)), this);
        parser sintactico = new parser(lex, this);
        sintactico.parse();
        if (!ManejadorDeErrores.hayError) {
            erroresTextArea.setText("NO EXISTEN ERRORES");
            manTablas.escribirCuartetos();
            //Ahora se escribe el codigo c
            ManejadorC manC = new ManejadorC(manTablas.getTablaDeCuarteto(), this);
            frameC.getCodigoCjTextArea().setText(manC.escribirProgramaC());
            //Ahora se escribe el assembler
            //Ahora se hace visible el frame
            frameC.setVisible(true);
        }
        ManejadorDeErrores.hayError = false;
    }

    public JTextArea getErroresTextArea() {
        return erroresTextArea;
    }

    public void setErroresTextArea(JTextArea erroresTextArea) {
        this.erroresTextArea = erroresTextArea;
    }

    public ManejadorDeTablas getManTablas() {
        return manTablas;
    }

    public void setManTablas(ManejadorDeTablas manTablas) {
        this.manTablas = manTablas;
    }

    public JTextArea getCodigo3dTextArea() {
        return codigo3dTextArea;
    }

}
