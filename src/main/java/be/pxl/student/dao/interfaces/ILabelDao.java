package be.pxl.student.dao.interfaces;

import be.pxl.student.entity.Label;

import java.util.List;

public interface ILabelDao {

    List<Label> read();

    boolean createLabel(Label label);

    boolean updateLabel(Label label);

    boolean deleteLabel(int id);

    Label readLabel(int id);

}
