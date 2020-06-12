package be.pxl.student.persistence.dao.interfaces;

import be.pxl.student.persistence.entity.Label;

import java.util.List;

public interface ILabelDao {

    List<Label> read();

    boolean createLabel(Label label);

    boolean updateLabel(Label label);

    boolean deleteLabel(int id);

    Label readLabel(int id);

}
