package ro.mpp2024.hospital_system.model.validator;

import ro.mpp2024.hospital_system.model.User;

public class UserValidator implements Validator<User>{
    @Override
    public void validate(User entity) throws ValidationException {
        String errors = "";
        if(entity.getUsername().equals("")){
            errors += "Username cannot be empty!\n";
        }
        if(entity.getPassword().equals("")){
            errors += "Password cannot be empty!\n";
        }
        if(entity.getUserType() == null){
            errors += "Role cannot be empty!\n";
        }
        if (entity.getCnp().length() != 13) {
            errors += "CNP must have 13 characters!\n";
        }
        if (entity.getFirstName().equals("")) {
            errors += "First name cannot be empty!\n";
        }
        if (entity.getLastName().equals("")) {
            errors += "Last name cannot be empty!\n";
        }
        if(!errors.equals("")){
            throw new ValidationException(errors);
        }
    }
}
