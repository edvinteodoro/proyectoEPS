/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gt.edu.usac.cunoc.ingenieria.eps.user;

import gt.edu.usac.cunoc.ingenieria.eps.process.ProcessReviewResource;
import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

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
    
    @GET
    @Path("/searchUsers")
    public List<UserDto> findUsers(@QueryParam("userId") String userId,
            @QueryParam("userDpi") String userDpi,
            @QueryParam("userFirstName") String userFirstName,
            @QueryParam("userLastName") String userLastName) throws UserException {
        return userFacade.getUser(new User(userId, userDpi, userFirstName, userLastName))
                .stream()
                .map(UserDto::new)
                .collect(Collectors.toList());
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void createUser(NewUserDto newUserDto) throws UserException {
        Career career;
        Rol rolUser = userFacade.getRolUser(new Rol(null, newUserDto.getRol())).get(0);
        User newUser = new User();
        List<UserCareer> userCareers = new ArrayList<>();
        for (UserCareerDto careerDto : newUserDto.getCareers()) {
            career = userFacade.getCareer(new Career(careerDto.getCode())).get(0);
            userCareers.add(new UserCareer(career, newUser));
        }
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
    public ProcessResource getProcessResounrce(){
        return processResource;
    }

    
    @Inject
    ProcessReviewResource processReviewResource;
    
    @Path("/{userId}/processes")
    public ProcessReviewResource getProcess(){
        return processReviewResource;
    }
    
}
