package gestatestados.mberges.tfg.web.rest;

import gestatestados.mberges.tfg.domain.Atestado;
import gestatestados.mberges.tfg.domain.Documento;
//import gestatestados.mberges.tfg.domain.Implicado;
//import gestatestados.mberges.tfg.domain.Destinatario;
//import gestatestados.mberges.tfg.domain.Remitente;
//import gestatestados.mberges.tfg.domain.TasaAlcohol;

import gestatestados.mberges.tfg.repository.AtestadoRepository;
import gestatestados.mberges.tfg.repository.DocumentoRepository;
import gestatestados.mberges.tfg.repository.ImplicadoRepository;
import gestatestados.mberges.tfg.repository.RemitenteRepository;
import gestatestados.mberges.tfg.repository.DestinatarioRepository;
import gestatestados.mberges.tfg.repository.TasaAlcoholRepository;

//import io.github.jhipster.web.util.HeaderUtil;
//import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//import java.net.URI;
//import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Set;
import java.text.SimpleDateFormat;
//import java.util.Date;
import java.time.format.DateTimeFormatter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

//import java.io.File;
//import java.io.FileOutputStream;
import java.io.FileInputStream;

import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.*;
//import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
//import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSectPr;
//import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTabStop;
//import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTabJc;

//import java.math.BigInteger;
 /*
 * REST controller for managing {@link gestatestados.mberges.tfg.domain.Atestado}.
 */
@RestController
@RequestMapping("/api")
public class InformeAtestadoWordResource {

    private final Logger log = LoggerFactory.getLogger(InformeAtestadoWordResource.class);
    private static final String ENTITY_NAME = "InformeAtestadoWord";
    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AtestadoRepository atestadoRepository;
    private final DocumentoRepository documentoRepository;
    //private final RemitenteRepository remitenteRepository;
    //private final DestinatarioRepository destinatarioRepository;
    //private final ImplicadoRepository implicadoRepository;
    //private final TasaAlcoholRepository tasaAlcoholRepository;

    public InformeAtestadoWordResource(AtestadoRepository atestadoRepository, DocumentoRepository documentoRepository,
                            RemitenteRepository remitenteRepository, DestinatarioRepository destinatarioRepository,
                            ImplicadoRepository implicadoRepository, TasaAlcoholRepository tasaAlcoholRepository) {
        this.atestadoRepository = atestadoRepository;
        this.documentoRepository = documentoRepository;
        //this.remitenteRepository = remitenteRepository;
        //this.destinatarioRepository = destinatarioRepository;
        //this.implicadoRepository = implicadoRepository;
        //this.tasaAlcoholRepository = tasaAlcoholRepository;
    }

    /**
     * {@code GET  /atestados} : get all the atestados.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of atestados in body.
     */
    @GetMapping("/informeAtestadoWord")
    public List<Atestado> getAllAtestados() {
        log.debug("REST request to get all Atestados");
        return atestadoRepository.findAll();
    }

    /**
     * {@code GET  /Informe/:id} : get the "id" atestado.
     *
     * @param id the id of the atestado to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the atestado, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/informeAtestadoWord/{id}")
    public void createInformeAtestadoWord(@PathVariable Long id, HttpServletResponse response) throws Exception {
        log.debug("REST request create Informe Atestado Word : {}", id);
        XWPFDocument doc = new XWPFDocument();

        //Crear archivo docx
        Optional<Atestado> atestado = this.atestadoRepository.findById(id);
        XWPFParagraph p1 = doc.createParagraph();
        try
        {
            crearCabecera(doc, p1, atestado.get().getNumero());
        }
        catch(Exception e)
        {
            log.debug("ERROR:", e.toString());
        }
        crearTablaDatosAtestado(doc, p1, atestado);
        XWPFParagraph p2 = doc.createParagraph();
        crearTablaDocumentosAtestado(doc,p2,atestado);
        //XWPFParagraph p2 = doc.createParagraph();

        response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        response.setHeader("Content-disposition", "filename=\"informeAtestado"+atestado.get().getNumero().trim().toString()+".docx\"");
        ServletOutputStream streamVar = response.getOutputStream();
        doc.write(streamVar);
        streamVar.flush();
        streamVar.close();
        doc.close();
    }
    private void crearTablaDocumentosAtestado(XWPFDocument doc, XWPFParagraph p2, Optional<Atestado> atestado)
    {
      p2.setAlignment(ParagraphAlignment.LEFT);
      XWPFRun p21 = p2.createRun();
      p21.setBold(true);
      p21.setItalic(true);
      //p21.setTextPosition(100);
      p21.setText("* Relación Documentos Atestado número "+atestado.get().getNumero().toString());
      p21.addBreak();
      p21.addBreak();
      p21.setBold(false);
      p21.setItalic(false);

      int numDoc = 1;
      List<Documento> listDocumentosByAtestado = documentoRepository.findDocumentosByAtestado(atestado.get().getId());
      for (Documento d : listDocumentosByAtestado) {
        p21.setText(" - Doc "+(numDoc++)+":"+d.getNombreDoc().toString());
        p21.addBreak();
        log.debug("NOMBREDOC:"+d.getNombreDoc());
       }

    }

    private void crearTablaDatosAtestado(XWPFDocument doc, XWPFParagraph p1, Optional<Atestado> atestado)
    {
      //p1 = doc.createParagraph();
      p1.setAlignment(ParagraphAlignment.LEFT);

      //título
      XWPFRun p11 = p1.createRun();

      p11.addBreak();
      p11.addBreak();
      p11.setBold(true);
      p11.setItalic(true);
      p11.setTextPosition(100);
      p11.setText("INFORME RESUMEN GENERAL ATESTADO "+atestado.get().getNumero().toString());
      p11.addBreak();

      XWPFRun p12 = p1.createRun();
      String strDateFormat = "dd-MM-aaaa hh:mm:ss"; // El formato de fecha está especificado 
      SimpleDateFormat objSDF = new SimpleDateFormat(strDateFormat);

      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy - HH:mm:ss Z");
      //LocalDate dt = LocalDate.parse("2018-11-01");
      //String formattedString = zonedDateTime.format(formatter);

      p12.addBreak();
      if(atestado.get().getFechaHoraSuceso() != null)
      {
        p12.setText("- FECHA/HORA SUCESO: "+atestado.get().getFechaHoraSuceso().format(formatter));
        p12.addBreak();
      }
      if(atestado.get().getLugar() != null)
      {
        p12.setText("- LUGAR: "+atestado.get().getLugar());
        p12.addBreak();
      }

      if(atestado.get().getInstructor() != null)
      {
        p12.setText("- INSTRUCTOR: "+atestado.get().getInstructor());
        p12.addBreak();
      }
      if(atestado.get().getSecretario() != null)
      {
        p12.setText("- SECRETARIO: "+atestado.get().getSecretario());
        p12.addBreak();
      }

      if(atestado.get().getTipojuicio() != null)
      {
        p12.setText("- TIPO DE JUICIO: "+atestado.get().getTipojuicio().toString());
        p12.addBreak();
      }
      if(atestado.get().getDestinatario() != null)
      {
        p12.setText("- DESTINATARIO: "+atestado.get().getDestinatario().getNombreDestinatario().toString());
        p12.addBreak();
      }
      if(atestado.get().getFechaAtestado() != null)
      {
        p12.setText("- FECHA REALIZACIÓN ATESTADO: "+atestado.get().getFechaAtestado());
        p12.addBreak();
      }
      if(atestado.get().getRemitente() != null)
      {
        p12.setText("- REMITENTE: "+atestado.get().getRemitente().getNombreRemitente().toString());
        p12.addBreak();
      }
      p12.setText("- TIPO ATESTADO: "+ this.getTipoAtestado(atestado));

      p12.addBreak();
      p12.addBreak();
      p12.addBreak();

      /*
      XWPFTable table = doc.createTable();

      XWPFTableRow tableRow1 = table.getRow(0);
      tableRow1.getCell(0).setText("LUGAR");
      tableRow1.addNewTableCell().setText(atestado.get().getLugar());

      XWPFTableRow tableRow2 = table.createRow();
      tableRow2.getCell(0).setText("FECHA SUCESO");
      tableRow2.getCell(1).setText(atestado.get().getFechaHoraSuceso().toString());

      XWPFTableRow tableRow3 = table.createRow();
      tableRow3.getCell(0).setText("");
      tableRow3.getCell(1).setText(atestado.get().getInstructor());

      XWPFTableRow tableRow4 = table.createRow();
      tableRow4.getCell(0).setText("");
      tableRow4.getCell(1).setText(atestado.get().getSecretario());

      XWPFTableRow tableRow5 = table.createRow();
      tableRow5.getCell(0).setText("");
      tableRow5.getCell(1).setText(atestado.get().getFechaAtestado().toString());
      */

    }

    private String getTipoAtestado(Optional<Atestado> atestado)
    {
      String tipoAtestado = "";

      if(atestado.get().isAccidente())
      {
        if(tipoAtestado=="")
            tipoAtestado = tipoAtestado + " ACCIDENTE DE TRÁFICO ";
        else
            tipoAtestado = tipoAtestado + " + ACCIDENTE DE TRÁFICO ";
      }
      if(atestado.get().isAlcoholemia())
      {
        if(tipoAtestado=="")
            tipoAtestado = tipoAtestado + " ALCOHOLEMIA ";
        else
            tipoAtestado = tipoAtestado + " + ALCOHOLEMIA ";
      }
      if(atestado.get().isVelocidad())
      {
        if(tipoAtestado=="")
            tipoAtestado = tipoAtestado + " EXCESO VELOCIDAD ";
        else
            tipoAtestado = tipoAtestado + " + EXCESO VELOCIDAD ";
      }
      if(atestado.get().isBienes())
      {
        if(tipoAtestado=="")
            tipoAtestado = tipoAtestado + " DAÑOS EN BIENES ";
        else
            tipoAtestado = tipoAtestado + " + DAÑOS EN BIENES ";
      }
      if(atestado.get().isPermiso())
      {
        if(tipoAtestado=="")
            tipoAtestado = tipoAtestado + " NO PERMISO DE CONDUCCIÓN ";
        else
            tipoAtestado = tipoAtestado + " + NO PERMISO DE CONDUCCIÓN ";
      }
      if(atestado.get().isLesiones())
      {
        if(tipoAtestado=="")
            tipoAtestado = tipoAtestado + " LESIONES ";
        else
            tipoAtestado = tipoAtestado + " + LESIONES ";
      }
      if(atestado.get().isFallecido())
      {
        if(tipoAtestado=="")
            tipoAtestado = tipoAtestado + " FALLECIDOS ";
        else
            tipoAtestado = tipoAtestado + " + FALLECIDOS ";
      }
      return tipoAtestado;
    }
    private void crearCabecera(XWPFDocument d, XWPFParagraph p, String numatestado) throws Exception
    {
        p.setAlignment(ParagraphAlignment.LEFT);
        XWPFRun r = p.createRun();

        String imgFile="./src/main/java/gestatestados/mberges/tfg/content/escudoPL.jpg";
        r.addPicture(new FileInputStream(imgFile), XWPFDocument.PICTURE_TYPE_JPEG, imgFile, Units.toEMU(50), Units.toEMU(50));
        String imgFile2="./src/main/java/gestatestados/mberges/tfg/content/ayunt2PL.jpg";
        r.addPicture(new FileInputStream(imgFile2), XWPFDocument.PICTURE_TYPE_JPEG, imgFile2, Units.toEMU(50), Units.toEMU(50));

        //XWPFParagraph p = d.createParagraph();
        p.setAlignment(ParagraphAlignment.RIGHT);

        XWPFRun r2 = p.createRun();
        r2.addTab();
        r2.addTab();
        r2.addTab();

        r2.setText("Diligencias Nº "+numatestado);
    }
}
