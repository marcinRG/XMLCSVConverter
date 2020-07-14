package modelTest;

import io.marcinrg.enums.FileTypesNames;
import io.marcinrg.model.AppState;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class AppStateClassTest {
    private AppState appState;

    @Before
    public void init() {
        this.appState = new AppState();
    }

    @Test
    public void AppStateClassObjectExists() {
        Assert.assertNotNull(appState);
    }

    @Test
    public void AppStateClassChangeFileType() {
        appState.setSelectedFileType(FileTypesNames.XML_PIT_11);
        Assert.assertEquals("Should be the same value", appState.getSelectedFileType().getFileType(), FileTypesNames.XML_PIT_11.getFileType());
    }

    @Test
    public void AppStateGetAppTitle() {
        Assert.assertEquals("Should be the same",appState.getAppTitle(),"Conwerter XML do CSV");
    }


}
