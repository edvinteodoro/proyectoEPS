/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gt.edu.usac.cunoc.ingenieria.eps.user;

import User.exception.UserException;
import gt.edu.usac.cunoc.ingenieria.eps.process.ProcessResource;
import gt.edu.usac.cunoc.ingenieria.eps.user.facade.UserFacadeLocal;
import gt.edu.usac.cunoc.ingenieria.eps.userDto.BasicUserDto;
import gt.edu.usac.cunoc.ingenieria.eps.userDto.CompleteUserDto;
import gt.edu.usac.cunoc.ingenieria.eps.userDto.NewUserDto;
import gt.edu.usac.cunoc.ingenieria.eps.userDto.UserCareerDto;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
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
    
    @Inject
    private ProcessResource processResource;

    @GET
    public Response findAllUsers() {
        try {
            return Response
                    .status(Response.Status.FOUND)
                    .entity(userFacade.getUser(new User())
                            .stream()
                            .map(CompleteUserDto::new)
                            .collect(Collectors.toList()))
                    .build();
        } catch (UserException e) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(Response.Status.BAD_REQUEST + ": " + e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("/{userId}")
    public Response findByUserId(@PathParam("userId") String userId) {
        Optional<User> user = userFacade.getUserByUserId(userId);
        if (user.isPresent()) {
            return Response
                    .status(Response.Status.FOUND)
                    .entity(new CompleteUserDto(user.get()))
                    .build();
        }
        return Response
                .status(Response.Status.NOT_FOUND)
                .entity(Response.Status.NOT_FOUND + ": User not found")
                .build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUser(NewUserDto newUserDto) {
        try {
            return Response
                    .status(Response.Status.CREATED)
                    .entity(new CompleteUserDto(userFacade.createUser(convertFromNewUser(newUserDto))))
                    .build();
        } catch (UserException ex) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(Response.Status.BAD_REQUEST + ": " + ex.getMessage())
                    .build();
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateUser(BasicUserDto updateUserDto) {
        try {
            return Response
                    .status(Response.Status.OK)
                    .entity(new CompleteUserDto(userFacade.updateUser(convertFromBasicUser(updateUserDto))))
                    .build();
        } catch (UserException ex) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(Response.Status.BAD_REQUEST + ": " + ex.getMessage())
                    .build();
        }
    }

    @PUT
    @Path("/resetPassword/{userId}/{emailUser}")
    public Response resetPassword(@PathParam("userId") String userId,
            @PathParam("emailUser") String emailUser) {
        try {
            return Response
                    .status(Response.Status.OK)
                    .entity(userFacade.resetPassword(userId, emailUser))
                    .build();
        } catch (UserException e) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(Response.Status.BAD_REQUEST + ": " + e.getMessage())
                    .build();
        }
    }

    @PUT
    @Path("/{userId}/committeeEPSStatus")
    public Response changeCommitteeStatus(@PathParam("userId") String userId) {
        Optional<User> user = userFacade.getUserByUserId(userId);
        if (user.isPresent()) {
            try {
                if (!user.get().getEpsCommittee()) {
                    user.get().setEpsCommittee(true);
                } else {
                    user.get().setEpsCommittee(false);
                }
                return Response
                        .status(Response.Status.OK)
                        .entity(new CompleteUserDto(userFacade.updateUser(user.get())))
                        .build();
            } catch (UserException ex) {
                return Response
                        .status(Response.Status.BAD_REQUEST)
                        .entity(Response.Status.BAD_REQUEST + ": " + ex.getMessage())
                        .build();
            }
        } else {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(Response.Status.NOT_FOUND + ": User not found")
                    .build();
        }
    }

    @PUT
    @Path("/{userId}/status")
    public Response changeStatus(@PathParam("userId") String userId) {
        Optional<User> user = userFacade.getUserByUserId(userId);
        if (user.isPresent()) {
            try {
                if (!user.get().getStatus()) {
                    user.get().setStatus(true);
                } else {
                    user.get().setStatus(false);
                }
                return Response
                        .status(Response.Status.OK)
                        .entity(new CompleteUserDto(userFacade.updateUser(user.get())))
                        .build();
            } catch (UserException ex) {
                return Response
                        .status(Response.Status.BAD_REQUEST)
                        .entity(Response.Status.BAD_REQUEST + ": " + ex.getMessage())
                        .build();
            }
        } else {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(Response.Status.NOT_FOUND + ": User not found")
                    .build();
        }
    }

    private User convertFromNewUser(NewUserDto userDto) throws UserException {
        Career career;
        Rol rolUser = userFacade.getRolUser(new Rol(null, userDto.getRol())).get(0);
        User newUser = convertFromBasicUser(userDto);
        List<UserCareer> userCareers = new ArrayList<>();
        for (UserCareerDto careerDto : userDto.getCareers()) {
            career = userFacade.getCareer(new Career(careerDto.getCode())).get(0);
            userCareers.add(new UserCareer(career, newUser));
        }
        newUser.setUserCareers(userCareers);
        newUser.setUserId(userDto.getId());
        newUser.setDpi(userDto.getDpi());
        newUser.setCodePersonal(userDto.getCodePersonal());
        newUser.setAcademicRegister(userDto.getAcademicRegister());
        newUser.setPassword(userDto.getPassword());
        newUser.setROLid(rolUser);
        return newUser;
    }

    private User convertFromBasicUser(BasicUserDto userDto) {
        User newUser = new User();
        newUser.setUserId(userDto.getId());
        newUser.setFirstName(userDto.getFirstName());
        newUser.setLastName(userDto.getLastName());
        newUser.setEmail(userDto.getEmail());
        newUser.setPhone1(userDto.getPhone1());
        newUser.setPhone2(userDto.getPhone2());
        newUser.setDirection(userDto.getDirection());
        return newUser;
    }
    
    @Path("/{userId}/processes")
    public ProcessResource getProcessResounrce() {
        return processResource;
    }

}
