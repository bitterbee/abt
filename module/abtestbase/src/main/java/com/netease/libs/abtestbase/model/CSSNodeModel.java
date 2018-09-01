package com.netease.libs.abtestbase.model;

import com.facebook.csslayout.CSSConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zyl06 on 2018/8/28.
 * {@link com.facebook.csslayout.CSSStyle}
 */
public class CSSNodeModel extends BaseModel {
    /**
     * {@link com.facebook.csslayout.CSSDirection}
     * 0: INHERIT, 1:LTR, 2: RTL
     */
    public String direction = "inherit";
    /**
     * {@link com.facebook.csslayout.CSSFlexDirection}
     * 0:COLUMN, 1:COLUMN_REVERSE, 2:ROW, 3:ROW_REVERSE
     */
    public String flexDirection = "column";
    /**
     * {@link com.facebook.csslayout.CSSJustify}
     * 0:FLEX_START,1:CENTER,2:FLEX_END,3:SPACE_BETWEEN,4:SPACE_AROUND
     */
    public String justifyContent = "flex_start";
    /**
     * {@link com.facebook.csslayout.CSSAlign}
     * 0:FLEX_START,1:AUTO,2:CENTER,3:FLEX_END,4:STRETCH
     */
    public String alignContent = "flex_start";
    /**
     * {@link com.facebook.csslayout.CSSAlign}
     * 0:FLEX_START,1:AUTO,2:CENTER,3:FLEX_END,4:STRETCH
     */
    public String alignItems = "stretch";
    /**
     * {@link com.facebook.csslayout.CSSAlign}
     * 0:FLEX_START,1:AUTO,2:CENTER,3:FLEX_END,4:STRETCH
     */
    public String alignSelf = "auto";
    /**
     * {@link com.facebook.csslayout.CSSPositionType}
     * 0:RELATIVE,1:ABSOLUTE
     */
    public String positionType = "relative";
    /**
     * {@link com.facebook.csslayout.CSSWrap}
     * 0:NOWRAP,1:WRAP
     */
    public String flexWrap = "nowrap";

    public float flex;

    /**
     * {@link com.facebook.csslayout.Spacing}
     */
    public List<Float> margin = new ArrayList<Float>() {
        {
            add(CSSConstants.UNDEFINED);
            add(CSSConstants.UNDEFINED);
            add(CSSConstants.UNDEFINED);
            add(CSSConstants.UNDEFINED);
        }
    };

    /**
     * {@link com.facebook.csslayout.Spacing}
     */
    public List<Float> padding = new ArrayList<Float>() {
        {
            add(CSSConstants.UNDEFINED);
            add(CSSConstants.UNDEFINED);
            add(CSSConstants.UNDEFINED);
            add(CSSConstants.UNDEFINED);
        }
    };

    /**
     * {@link com.facebook.csslayout.Spacing}
     */
    public List<Float> border = new ArrayList<Float>() {
        {
            add(CSSConstants.UNDEFINED);
            add(CSSConstants.UNDEFINED);
            add(CSSConstants.UNDEFINED);
            add(CSSConstants.UNDEFINED);
        }
    };

    public List<Float> position = new ArrayList<Float>(4) {
        {
            add(CSSConstants.UNDEFINED);
            add(CSSConstants.UNDEFINED);
            add(CSSConstants.UNDEFINED);
            add(CSSConstants.UNDEFINED);
        }
    };

    public List<Float> dimensions = new ArrayList<Float>(2) {
        {
            add(CSSConstants.UNDEFINED);
            add(CSSConstants.UNDEFINED);
        }
    };

    public float minWidth = CSSConstants.UNDEFINED;
    public float minHeight = CSSConstants.UNDEFINED;

    public float maxWidth = CSSConstants.UNDEFINED;
    public float maxHeight = CSSConstants.UNDEFINED;

    public boolean sizetofit = false;

    public int index;

    public List<CSSNodeModel> children = new ArrayList<>();
}
