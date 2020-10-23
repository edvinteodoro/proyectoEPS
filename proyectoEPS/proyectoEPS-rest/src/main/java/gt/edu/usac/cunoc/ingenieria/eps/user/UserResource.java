/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gt.edu.usac.cunoc.ingenieria.eps.user;

import User.exception.UserException;
import gt.edu.usac.cunoc.ingenieria.eps.process.ProcessResource;
import gt.edu.usac.cunoc.ingenieria.eps.process.facade.ProcessFacadeLocal;
import gt.edu.usac.cunoc.ingenieria.eps.processDto.ProcessDto;
import gt.edu.usac.cunoc.ingenieria.eps.user.facade.UserFacadeLocal;
import gt.edu.usac.cunoc.ingenieria.eps.userDto.NewUserDto;
import gt.edu.usac.cunoc.ingenieria.eps.userDto.UserCareerDto;
import gt.edu.usac.cunoc.ingenieria.eps.userDto.UserDto;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author crystian
 */
@Path("/users")
@Produces("application/json")
public class UserResource {

    @EJB
    private UserFacadeLocal userFacade;

    @EJB
    private ProcessFacadeLocal processFacade;

    @Inject
    ProcessResource processResource;
    
    @Inject
    AuthUserResource authUserResource;

    @GET
    public Response getUsers() throws UserException {
        try {
            List<UserDto> users = userFacade.getUser(new User()).stream()
                    .map(user -> new UserDto(user))
                    .collect(Collectors.toList());
            return Response
                    .status(Response.Status.OK)
                    .entity(users).build();
        } catch (Exception e) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .build();
        }
    }

//    @GET
//    @Path("/searchUsers")
//    public List<UserDto> findUsers(@QueryParam("userId") String userId,
//            @QueryParam("userDpi") String userDpi,
//            @QueryParam("userFirstName") String userFirstName,
//            @QueryParam("userLastName") String userLastName) throws UserException {
//        return userFacade.getUser(new User(userId, userDpi, userFirstName, userLastName))
//                .stream()
//                .map(UserDto::new)
//                .collect(Collectors.toList());
//    }
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void createUser(NewUserDto newUserDto) throws UserException {
        Career career;
        Rol rolUser = userFacade.getRolUser(new Rol(null, newUserDto.getRol())).get(0);
        User newUser = new User();
        List<UserCareer> userCareers = new ArrayList<>();
//        for (UserCareerDto careerDto : newUserDto.getCareers()) {
//            career = userFacade.getCareer(new Career(careerDto.getCode())).get(0);
//            userCareers.add(new UserCareer(career, newUser));
//        }
        newUser.setUserCareers(userCareers);
        newUser.setUserId(newUserDto.getId());
        newUser.setDpi(newUserDto.getDpi());
        newUser.setCodePersonal(newUserDto.getCodePersonal());
        newUser.setAcademicRegister(newUserDto.getAcademicRegister());
        newUser.setFirstName(newUserDto.getFirstName());
        newUser.setLastName(newUserDto.getLastName());
        newUser.setEmail(newUserDto.getEmail());
        newUser.setPhone1(newUserDto.getPhone1());
        newUser.setPhone2(newUserDto.getPhone2());
        newUser.setPassword(newUserDto.getPassword());
        newUser.setDirection(newUserDto.getDirection());
        newUser.setROLid(rolUser);
        userFacade.createUser(newUser);
    }

    @Path("/{userId}/processes")
    public ProcessResource getProcessResounrce() {
        return processResource;
    }

    @Path("/{userId}/auth")
    public AuthUserResource getAuthUserResounrce() {
        return authUserResource;
    }
}
