package ro.mpp2024.hospital_system.model.validator;

import ro.mpp2024.hospital_system.model.PrescriptionDetail;

public class PrescriptionDetailValidator implements Validator<PrescriptionDetail> {

    @Override
    public void validate(PrescriptionDetail entity) throws ValidationException {
        String errors = "";
        if (entity.getDrugId() == null) {
            errors += "This drug does not exist\n";
        }
        if (entity.getPrescriptionId() == null) {
            errors += "This prescription does not exist\n";
        }
        if (entity.getQuantity() <= 0) {
            errors += "Quantity is invalid\n";
        }
        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }
}
