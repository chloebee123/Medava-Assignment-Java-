package edu.uc.cs3003.medava;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
public class Pharmacy {
    public Pharmacy(String pharmacyName) {
        mPharmacyName = pharmacyName;
    }
    public boolean send(Transporter t) {
        Medicine advil = new Ibuprofen();
        if (t.load(advil)) {
            System.out.println(String.format("Sending %s on the %s transporter.", advil.getMedicineName(),
                    t.getTransporterName()));
        } else {
            System.out.println(String.format("Cannot load %s on to the %s transporter.", advil.getMedicineName(),
                    t.getTransporterName()));
            return false;
        }

        Medicine activase = new Thrombolytic();
        if (t.load(activase)) {
            System.out.println(String.format("Sending %s on the %s transporter.", activase.getMedicineName(),
                    t.getTransporterName()));
        } else {
            System.out.println(String.format("Cannot load %s on to the %s transporter.", activase.getMedicineName(),
                    t.getTransporterName()));
            return false;
        }

        Medicine oxycontin = new Oxycodone();
        if (t.load(oxycontin)) {
            System.out.println(String.format("Sending %s on the %s transporter.", oxycontin.getMedicineName(),
                    t.getTransporterName()));
        } else {
            System.out.println(String.format("Cannot load %s on to the %s transporter.", oxycontin.getMedicineName(),
                    t.getTransporterName()));
            return false;
        }

        Jarvik heart = new Jarvik("01j9a9lk71");
        if (t.load(heart)) {
            System.out.println(String.format("Sending %s on the %s transporter.", heart.getMedicineName(),
                    t.getTransporterName()));
        } else {
            System.out.println(String.format("Cannot load %s on to the %s transporter.", heart.getMedicineName(),
                    t.getTransporterName()));
            return false;
        }

        return true;
    }
    
    void receive(Transporter t) {
        while (!t.isEmpty()) {
            try {
                Object unloaded = t.unload();
                Method getScheduleMethod = unloaded.getClass().getMethod("getSchedule");
                MedicineSchedule getScheduleMethodResult = (MedicineSchedule) getScheduleMethod.invoke(unloaded);
                Method getMedicineNameMethod = unloaded.getClass().getMethod("getMedicineName");
                String getMedicineNameMethodResult = (String) getMedicineNameMethod.invoke(unloaded);
                System.out.println(String.format("Checking whether Hospital can receive %s.", getMedicineNameMethodResult));
                if (getScheduleMethodResult != MedicineSchedule.Uncontrolled) {
                    System.out.println(String.format("Hospital cannot receive controlled substances and %s is a %s.",
                            getMedicineNameMethodResult, getScheduleMethodResult.asString()));
                } else {
                    System.out.println(String.format("Accepted a shipment of %s.", getMedicineNameMethodResult));
                }
            } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
                    | InvocationTargetException e) {
                // No need to do anything
            }
        }
    }

    public String pharmacyName() {
        return mPharmacyName;
    }

    private String mPharmacyName;
}