package ro.mpp2024.hospital_system.model.validator;

import ro.mpp2024.hospital_system.model.Drug;

public class DrugValidator implements Validator<Drug> {
    @Override
    public void validate(Drug entity) throws ValidationException {
        String errors = "";
        if (entity.getName().isEmpty()) {
            errors += "Name cannot be empty!\n";
        }
        if (entity.getContraindications().isEmpty()) {
            errors += "Contraindications cannot be empty!\n";
        }
        if (entity.getStock() < 0) {
            errors += "Stock cannot be negative!\n";
        }

        if (!errors.equals("")) {
            throw new ValidationException(errors);
        }

    }


}
