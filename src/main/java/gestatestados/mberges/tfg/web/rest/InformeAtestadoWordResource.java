package gestatestados.mberges.tfg.web.rest;

import gestatestados.mberges.tfg.domain.Atestado;
import gestatestados.mberges.tfg.domain.Documento;
import gestatestados.mberges.tfg.domain.Implicado;
import gestatestados.mberges.tfg.domain.Destinatario;
import gestatestados.mberges.tfg.domain.Remitente;
import gestatestados.mberges.tfg.domain.TasaAlcohol;

import gestatestados.mberges.tfg.repository.AtestadoRepository;
import gestatestados.mberges.tfg.repository.DocumentoRepository;
import gestatestados.mberges.tfg.repository.ImplicadoRepository;
import gestatestados.mberges.tfg.repository.RemitenteRepository;
import gestatestados.mberges.tfg.repository.DestinatarioRepository;
import gestatestados.mberges.tfg.repository.TasaAlcoholRepository;

import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.Borders;
import org.apache.poi.xwpf.usermodel.BreakClear;
import org.apache.poi.xwpf.usermodel.BreakType;
import org.apache.poi.xwpf.usermodel.LineSpacingRule;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.TextAlignment;
import org.apache.poi.xwpf.usermodel.UnderlinePatterns;
import org.apache.poi.xwpf.usermodel.VerticalAlign;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;

import org.apache.poi.util.Units;

import org.apache.poi.xwpf.usermodel.*;

import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;

import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSectPr;

import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTabStop;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTabJc;

import java.math.BigInteger;
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
    private final RemitenteRepository remitenteRepository;
    private final DestinatarioRepository destinatarioRepository;
    private final ImplicadoRepository implicadoRepository;
    private final TasaAlcoholRepository tasaAlcoholRepository;

    public InformeAtestadoWordResource(AtestadoRepository atestadoRepository, DocumentoRepository documentoRepository,
                            RemitenteRepository remitenteRepository, DestinatarioRepository destinatarioRepository,
                            ImplicadoRepository implicadoRepository, TasaAlcoholRepository tasaAlcoholRepository) {
        this.atestadoRepository = atestadoRepository;
        this.documentoRepository = documentoRepository;
        this.remitenteRepository = remitenteRepository;
        this.destinatarioRepository = destinatarioRepository;
        this.implicadoRepository = implicadoRepository;
        this.tasaAlcoholRepository = tasaAlcoholRepository;
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
        Optional<Atestado> atestado = atestadoRepository.findById(id);
        XWPFParagraph p1 = doc.createParagraph();

        /*
        p1.setAlignment(ParagraphAlignment.CENTER);
        p1.setBorderBottom(Borders.DOUBLE);
        p1.setBorderTop(Borders.DOUBLE);

        p1.setBorderRight(Borders.DOUBLE);
        p1.setBorderLeft(Borders.DOUBLE);
        p1.setBorderBetween(Borders.SINGLE);

        p1.setVerticalAlignment(TextAlignment.TOP);

        XWPFRun r1 = p1.createRun();
        r1.setBold(true);
        r1.setText("Número Atestado:"+atestado.get().getNumero().toString());
        r1.setBold(true);
        r1.setFontFamily("Courier");
        r1.setUnderline(UnderlinePatterns.DOT_DOT_DASH);
        r1.setTextPosition(100);

        r1.setBold(true);
        r1.setText("Lugar:"+atestado.get().getLugar().toString());
        r1.setBold(true);
        r1.setFontFamily("Courier");
        r1.setUnderline(UnderlinePatterns.DOT_DOT_DASH);
        r1.setTextPosition(100);

        r1.setBold(true);
        r1.setText("Fecha:"+atestado.get().getFechaAtestado().toString());
        r1.setBold(true);
        r1.setFontFamily("Courier");
        r1.setUnderline(UnderlinePatterns.DOT_DOT_DASH);
        r1.setTextPosition(100);
        */

        XWPFParagraph p2 = doc.createParagraph();
        p2.setAlignment(ParagraphAlignment.RIGHT);
        p2.setBorderBottom(Borders.DOUBLE);
        p2.setBorderTop(Borders.DOUBLE);
        p2.setBorderRight(Borders.DOUBLE);
        p2.setBorderLeft(Borders.DOUBLE);
        p2.setBorderBetween(Borders.SINGLE);

        XWPFRun r2 = p2.createRun();
/*
        int numDoc = 1;
        List<Documento> listDocumentosByAtestado = documentoRepository.findDocumentosByAtestado(id);
        for (Documento d : listDocumentosByAtestado) {
            r2.setText("Doc "+(numDoc++)+":"+d.getNombreDoc().toString());
            r2.setStrikeThrough(true);
            r2.setFontSize(20);
            log.debug("NOMBREDOC:"+d.getNombreDoc());
           }
*/
        // create header
        CTSectPr sectPr = doc.getDocument().getBody().addNewSectPr();
        log.debug("Ctsectpr");
        XWPFHeaderFooterPolicy headerFooterPolicy = new XWPFHeaderFooterPolicy(doc, sectPr);
        log.debug("xwpfheaderfooter");

        XWPFHeader header = headerFooterPolicy.createHeader(XWPFHeaderFooterPolicy.DEFAULT);
        log.debug("header");

        //p2 = header.getParagraphArray(0);
        p2.setAlignment(ParagraphAlignment.LEFT);

        //CTTabStop tabStop = p2.getCTP().getPPr().addNewTabs().addNewTab();
        //log.debug("tabstop");

        //tabStop.setVal(STTabJc.RIGHT);
        //int twipsPerInch =  1440;
        //tabStop.setPos(BigInteger.valueOf(6 * twipsPerInch));

        //r2 = p2.createRun();
        //r2.setText("The Header:");
        //r2.addTab();

        String imgFile="./src/main/java/gestatestados/mberges/tfg/content/escudoPL.jpg";
        r2.addPicture(new FileInputStream(imgFile), XWPFDocument.PICTURE_TYPE_JPEG, imgFile, Units.toEMU(50), Units.toEMU(50));
        //String imgFile2="../../content/ayunt2PL.jpg";
        String imgFile2="./src/main/java/gestatestados/mberges/tfg/content/ayunt2PL.jpg";
        r2.addPicture(new FileInputStream(imgFile2), XWPFDocument.PICTURE_TYPE_JPEG, imgFile2, Units.toEMU(50), Units.toEMU(50));

        log.debug("create footer");
        // create footer start
        XWPFFooter footer = headerFooterPolicy.createFooter(XWPFHeaderFooterPolicy.DEFAULT);
        log.debug("create footer");

        //p2 = footer.getParagraphArray(0);
        p2.setAlignment(ParagraphAlignment.LEFT);

        r2 = p2.createRun();
        //r2.setText("The Footer:");

        //try (FileOutputStream out = new FileOutputStream("simple.docx")) {
        //    doc.write(out);
        //}
        log.debug("vamoosossss",doc.toString());


        response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        response.setHeader("Content-disposition", "filename=\"informeAtestado.docx\"");
        ServletOutputStream streamVar = response.getOutputStream();
        doc.write(streamVar);
        streamVar.flush();
        streamVar.close();
        doc.close();
    }

}
