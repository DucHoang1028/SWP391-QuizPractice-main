package Models;

/**
 *
 * @author uchih
 */
public class MenuRole{
    private String name;
    private String link;
    private String svgtag;

    public MenuRole(String name, String link, String svgtag) {
        this.name = name;
        this.link = link;
        this.svgtag = svgtag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getSvgtag() {
        return svgtag;
    }

    public void setSvgtag(String svgtag) {
        this.svgtag = svgtag;
    }
    
    
}
