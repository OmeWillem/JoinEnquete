package eu.omewillem.joinenquete.utils.enums;

import eu.omewillem.joinenquete.JoinEnquete;
import eu.omewillem.joinenquete.utils.Utils;

public enum Choice {
    SITES,
    FRIEND,
    SOCIALMEDIA,
    DISCORD,
    OTHER;

    public String getDisplay() {
        return Utils.parse(JoinEnquete.getInstance()
                .getConfig().getString("display." + this.name().toLowerCase(), "NULL"));
    }

}