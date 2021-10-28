package hw12.family.FamilyDAO;

import hw12.family.People.Family;

import java.util.List;

public interface FamilyDAO {

    List<Family> getAllFamilies();
    Family getFamilyByIndex(int i);
    boolean deleteFamily(int i);
    boolean deleteFamily(Family o);
    boolean saveFamily(Family o);
    boolean deleteChild(int famIndex, int childIndex);
    public void saveList(List<Family> fL);
}
