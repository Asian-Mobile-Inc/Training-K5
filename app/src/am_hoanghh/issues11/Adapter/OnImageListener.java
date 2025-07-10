package issues11.Adapter;

import java.util.List;

import issues11.Model.Image;

public interface OnImageListener {
    void onUploadImage();
    void onSubtractIcon(int itemDelete);
    void onUpdateImageLists(List<Image> imageLists);
}
