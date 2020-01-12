/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gnz.backend.manejadorC;

import gnz.backend.cuarteto.Cuarteto;
import gnz.backend.cuarteto.TipoDeCuarteto;
import gnz.gui.frames.EditorDeTextoFrame;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 *
 * @author jesfrin
 */
public class ManejadorAssembler {

    private final static String INICIO_ASSEMBLER
            = "%include        'functions.asm'\n"
            + "\n";

    private String seccionBss;
    private String seccionText;
    private String seccionData;
    private static final String RESW = " resw 2\n";

    private static String PINTAR_NUMERO = "  call    iprintLF\n";

    private EditorDeTextoFrame editor;
    private LinkedList<Cuarteto> listaDeCuartetos;

    public ManejadorAssembler(LinkedList<Cuarteto> listaDeCuartetos, EditorDeTextoFrame editor) {
        this.editor = editor;
        this.seccionBss = "SECTION .bss\n";
        this.seccionText = "\nSECTION .text\nglobal _start\n\n_start:\n";
        this.seccionData = "SECTION .data\n\n";
        this.listaDeCuartetos = listaDeCuartetos;
    }

    public String escribirCodigoAssembler() {
        escribirDeclaracionesAsignaciones();
        escribirMain();
        return seccionData + seccionBss + seccionText;
    }

    private void escribirDeclaracionesAsignaciones() {
        String expresiones = "";
        LinkedList<Cuarteto> expresionesGlobales = new LinkedList<>();
        for (Cuarteto cuarteto : listaDeCuartetos) {
            if (cuarteto.getTipoDeCuarteto() == TipoDeCuarteto.GOTO && cuarteto.getResultado().equalsIgnoreCase("main")) {
                break;
            } else {
                expresionesGlobales.add(cuarteto);
            }
        }
        analisisDeCuartetos(expresionesGlobales);
    }

    private void escribirMain() {
        LinkedList<Cuarteto> contenidoDeMain = new LinkedList<>();
        boolean seEstaEnMain = false;
        for (Cuarteto cuarteto : listaDeCuartetos) {
            if (seEstaEnMain) {
                contenidoDeMain.add(cuarteto);
            } else {
                if (cuarteto.getTipoDeCuarteto() == TipoDeCuarteto.INICIO_FUNCION && cuarteto.getResultado().equals("MAIN")) {
                    seEstaEnMain = true;
                }
            }

        }
        analisisDeCuartetos(contenidoDeMain);
    }

    private void analisisDeCuartetos(LinkedList<Cuarteto> cuartetos) {
        for (Cuarteto cuarteto : cuartetos) {
            switch (cuarteto.getTipoDeCuarteto()) {
                case DECLARACION: {
                    seccionBss += cuarteto.getResultado() + RESW;
                    break;
                }
                case ASIGNACION_DECLARACION: {
                    seccionBss += cuarteto.getResultado() + RESW;
                    break;
                }
                case SOLO_ASIGNACION: {
                    seccionText += "mov " + cuarteto.getResultado() + "," + cuarteto.getOperador1() + "\n";
                    break;
                }
                case SOLO_EXPRESION: {
                    seccionBss += cuarteto.getResultado() + RESW;
                    switch (cuarteto.getOperando()) {
                        case "+":
                            seccionText += "add " + "eax" + "," + cuarteto.getOperador1() + "\n";
                            seccionText += "add " + "eax" + "," + cuarteto.getOperador2() + "\n";
                            break;
                        case "*":
                            seccionText += "mul " + "eax" + "," + cuarteto.getOperador1() + "\n";
                            seccionText += "mul " + "eax" + "," + cuarteto.getOperador2() + "\n";
                            break;
                        case "-":
                            break;
                        case "/":
                            seccionText += "div " + "eax" + "," + cuarteto.getOperador1() + "\n";
                            seccionText += "div " + "eax" + "," + cuarteto.getOperador2() + "\n";
                            break;
                    }
                    break;
                }
            }
        }
    }

}
