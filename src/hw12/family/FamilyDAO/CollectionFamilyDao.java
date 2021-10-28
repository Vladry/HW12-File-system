package hw12.family.FamilyDAO;

import hw12.family.People.Family;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class CollectionFamilyDao implements FamilyDAO {

    private final List<Family> memoryStore;

    {
        memoryStore = new LinkedList<Family>();
    }

    public List<Family> getAllFamilies() {
        return memoryStore;
    }

    public Family getFamilyByIndex(int i) {
        try {
            return memoryStore.get(i);
        } catch (NullPointerException e) {
            return null;
        }
    }

    public boolean deleteFamily(int i) {
        System.out.println("deleting family: " + memoryStore.get(i));
        try {
            memoryStore.remove(i);
            System.out.println("delete operation successful!");
//            System.out.println("updated family list: ");
//            System.out.println(memoryStore);
            return true;
        } catch (NullPointerException e) {
            return false;
        }
    }

    public boolean deleteFamily(Family o) { //TODO дописать
        Family testFam = null;
        try {
            memoryStore.remove(o);
            return true;
        } catch (NullPointerException e) {
            return false;
        }
    }

    public boolean saveFamily(Family o) {
        if (memoryStore.contains(o)) {
            memoryStore.set(memoryStore.indexOf(o), o);
        } else {
            memoryStore.add(o);

        }
        return true;
    }

    public boolean deleteChild(int famIndex, int childIndex) {
        memoryStore.get(famIndex).getChildren().remove(childIndex);
        return true;
    }

    public void saveList(List<Family> fL){
        Collections.copy(fL, memoryStore);
    };
}



