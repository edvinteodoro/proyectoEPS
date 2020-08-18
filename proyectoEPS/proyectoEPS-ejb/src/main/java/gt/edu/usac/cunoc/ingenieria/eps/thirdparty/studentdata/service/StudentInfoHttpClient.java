package gt.edu.usac.cunoc.ingenieria.eps.thirdparty.studentdata.service;

import gt.edu.usac.cunoc.ingenieria.eps.exception.HttpClientException;
import gt.edu.usac.cunoc.ingenieria.eps.thirdparty.studentdata.StudentData;
import java.io.StringReader;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

/**
 * proyectoEPS-ejb
 *
 * @author jose - 10.08.2020
 * @Title: StudentInfoHttpClient
 * @Description: description
 *
 * Changes History
 */
@Stateless
@LocalBean
public class StudentInfoHttpClient {

    private static final String CUNOC_STUDENT_POINT = "http://ryca.cunoc.edu.gt/serviciosweb/serviceryca.php";
    private static final String CUNOC_STUDENT_POINT_CARNET = "carne";
    private static final String CUNOC_STUDENT_POINT_KEY = "key";
    private static final String CUNOC_STUDENT_POINT_KEY_VALUE = "rgtsd59TT";
    private static final String EMPTY_CARNET = "0";

    /**
     * Get the student data based on the carne
     *
     * @param carnet
     * @return
     * @throws HttpClientException
     */
    public Optional<StudentData> getStudentData(String carnet) throws HttpClientException {
        try {
            WebTarget target = ClientBuilder
                    .newClient()
                    .target(CUNOC_STUDENT_POINT);
            String studentRaw = target
                    .queryParam(CUNOC_STUDENT_POINT_CARNET, carnet)
                    .queryParam(CUNOC_STUDENT_POINT_KEY, CUNOC_STUDENT_POINT_KEY_VALUE)
                    .request(MediaType.APPLICATION_XML_TYPE)
                    .get(String.class);

            JAXBContext jaxbContext = JAXBContext.newInstance(StudentData.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            StudentData studentData = (StudentData) unmarshaller.unmarshal(new StringReader(studentRaw));
            if (EMPTY_CARNET.equals(studentData.getCarne())) {
                return Optional.empty();
            }
            return Optional.of(studentData);
        } catch (WebApplicationException ex) {
            Logger.getLogger(StudentInfoHttpClient.class.getName())
                    .log(Level.SEVERE, String.format(
                            "Error fetching student data from %s. %s: %s, %s, %s ",
                            CUNOC_STUDENT_POINT,
                            CUNOC_STUDENT_POINT_CARNET,
                            carnet,
                            CUNOC_STUDENT_POINT_KEY,
                            CUNOC_STUDENT_POINT_KEY_VALUE
                    ), ex);
            throw new HttpClientException("Error al obtener datos del estudiante " + carnet);
        } catch (JAXBException ex) {
            Logger.getLogger(StudentInfoHttpClient.class.getName()).log(Level.SEVERE, null, ex);
            throw new HttpClientException("Error procesando datos del estudiante " + carnet);
        }
    }
}
