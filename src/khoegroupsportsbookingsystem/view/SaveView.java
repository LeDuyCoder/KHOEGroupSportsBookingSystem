package khoegroupsportsbookingsystem.view;

import khoegroupsportsbookingsystem.business.controller.SaveController;
import khoegroupsportsbookingsystem.util.Acceptable;
import khoegroupsportsbookingsystem.util.Inputter;

public class SaveView {
    private final SaveController saveController;
    private final Inputter inputter;

    public SaveView(SaveController saveController, Inputter inputter) {
        this.saveController = saveController;
        this.inputter = inputter;
    }

    public void showSaveView(){
        System.out.println("===== Save Data =====");
        if(saveController.isChanged()){
            try {
                String choice = inputter.getStringInput("Data has changed. Do you want to save changes? (Y/N): ", Acceptable.CONFIRM_VALID);
                if(choice.equalsIgnoreCase("Y")){
                    saveController.saveChanges();
                    System.out.println("Data saved successfully.");
                } else {
                    System.out.println("Changes were not saved.");
                }
            } catch (Exception e) {
            }
        } else {
            System.out.println("No changes detected. No need to save.");
        }

    }
    
}
