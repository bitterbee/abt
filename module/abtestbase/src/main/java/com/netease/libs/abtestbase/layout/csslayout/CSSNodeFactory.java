package com.netease.libs.abtestbase.layout.csslayout;

import com.facebook.csslayout.CSSAlign;
import com.facebook.csslayout.CSSDirection;
import com.facebook.csslayout.CSSFlexDirection;
import com.facebook.csslayout.CSSJustify;
import com.facebook.csslayout.CSSLayout;
import com.facebook.csslayout.CSSNode;
import com.facebook.csslayout.CSSPositionType;
import com.facebook.csslayout.CSSWrap;
import com.facebook.csslayout.Spacing;
import com.netease.libs.abtestbase.model.CSSNodeModel;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zyl06 on 2018/8/29.
 */

public class CSSNodeFactory {

    private static final Map<String, CSSDirection> DIRECTION_MAP = new HashMap<>();
    static {
        DIRECTION_MAP.put("inherit", CSSDirection.INHERIT);
        DIRECTION_MAP.put("ltr", CSSDirection.LTR);
        DIRECTION_MAP.put("rtl", CSSDirection.RTL);
    }

    private static final Map<String, CSSFlexDirection> FLEX_DIRECTION_MAP = new HashMap<>();
    static {
        FLEX_DIRECTION_MAP.put("column", CSSFlexDirection.COLUMN);
        FLEX_DIRECTION_MAP.put("column_reverse", CSSFlexDirection.COLUMN_REVERSE);
        FLEX_DIRECTION_MAP.put("row", CSSFlexDirection.ROW);
        FLEX_DIRECTION_MAP.put("row_reverse", CSSFlexDirection.ROW_REVERSE);
    }

    private static final Map<String, CSSJustify> CSS_JUSTIFY_MAP = new HashMap<>();
    static {
        CSS_JUSTIFY_MAP.put("flex_start", CSSJustify.FLEX_START);
        CSS_JUSTIFY_MAP.put("center", CSSJustify.CENTER);
        CSS_JUSTIFY_MAP.put("flex_end", CSSJustify.FLEX_END);
        CSS_JUSTIFY_MAP.put("space_between", CSSJustify.SPACE_BETWEEN);
        CSS_JUSTIFY_MAP.put("space_around", CSSJustify.SPACE_AROUND);
    }

    private static final Map<String, CSSAlign> CSS_ALIGN_MAP = new HashMap<>();
    static {
        CSS_ALIGN_MAP.put("auto", CSSAlign.AUTO);
        CSS_ALIGN_MAP.put("flex_start", CSSAlign.FLEX_START);
        CSS_ALIGN_MAP.put("center", CSSAlign.CENTER);
        CSS_ALIGN_MAP.put("flex_end", CSSAlign.FLEX_END);
        CSS_ALIGN_MAP.put("stretch", CSSAlign.STRETCH);
    }

    private static final Map<String, CSSPositionType> CSS_POSITION_TYPE_MAP = new HashMap<>();
    static {
        CSS_POSITION_TYPE_MAP.put("relative", CSSPositionType.RELATIVE);
        CSS_POSITION_TYPE_MAP.put("absolute", CSSPositionType.ABSOLUTE);
    }

    private static final Map<String, CSSWrap> CSS_WRAP_MAP = new HashMap<>();
    static {
        CSS_WRAP_MAP.put("nowrap", CSSWrap.NOWRAP);
        CSS_WRAP_MAP.put("wrap", CSSWrap.WRAP);
    }

    public static CSSNode newNode(CSSNodeModel model) {
        CSSNode node = new CSSNode();
        node.reset();
        node.resetChildren();

        CSSDirection dir = DIRECTION_MAP.get(model.direction);
        if (dir != null) {
            node.setDirection(dir);
        }

        CSSFlexDirection flexDir = FLEX_DIRECTION_MAP.get(model.flexDirection);
        if (flexDir != null) {
            node.setFlexDirection(flexDir);
        }

        CSSJustify justifyContent = CSS_JUSTIFY_MAP.get(model.justifyContent);
        if (justifyContent != null) {
            node.setJustifyContent(justifyContent);
        }

        CSSAlign alignContent = CSS_ALIGN_MAP.get(model.alignContent);
        if (alignContent != null) {
            node.setAlignContent(alignContent);
        }

        CSSAlign alignItems = CSS_ALIGN_MAP.get(model.alignItems);
        if (alignItems != null) {
            node.setAlignItems(alignItems);
        }

        CSSAlign alignSelf = CSS_ALIGN_MAP.get(model.alignSelf);
        if (alignSelf != null) {
            node.setAlignSelf(alignSelf);
        }

        CSSPositionType positionType = CSS_POSITION_TYPE_MAP.get(model.positionType);
        if (positionType != null) {
            node.setPositionType(positionType);
        }

        CSSWrap flexWrap = CSS_WRAP_MAP.get(model.flexWrap);
        if (flexWrap != null) {
            node.setWrap(flexWrap);
        }

        node.setFlex(model.flex);

        node.setMargin(Spacing.LEFT, model.margin.get(Spacing.LEFT));
        node.setMargin(Spacing.TOP, model.margin.get(Spacing.TOP));
        node.setMargin(Spacing.RIGHT, model.margin.get(Spacing.RIGHT));
        node.setMargin(Spacing.BOTTOM, model.margin.get(Spacing.BOTTOM));

        node.setPadding(Spacing.LEFT, model.padding.get(Spacing.LEFT));
        node.setPadding(Spacing.TOP, model.padding.get(Spacing.TOP));
        node.setPadding(Spacing.RIGHT, model.padding.get(Spacing.RIGHT));
        node.setPadding(Spacing.BOTTOM, model.padding.get(Spacing.BOTTOM));

        node.setBorder(Spacing.LEFT, model.border.get(Spacing.LEFT));
        node.setBorder(Spacing.TOP, model.border.get(Spacing.TOP));
        node.setBorder(Spacing.RIGHT, model.border.get(Spacing.RIGHT));
        node.setBorder(Spacing.BOTTOM, model.border.get(Spacing.BOTTOM));

        node.setPositionLeft(model.position.get(Spacing.LEFT));
        node.setPositionTop(model.position.get(Spacing.TOP));
        node.setPositionRight(model.position.get(Spacing.RIGHT));
        node.setPositionBottom(model.position.get(Spacing.BOTTOM));

        node.setStyleWidth(model.dimensions.get(CSSLayout.DIMENSION_WIDTH));
        node.setStyleHeight(model.dimensions.get(CSSLayout.DIMENSION_HEIGHT));

        node.setStyleMinWidth(model.minWidth);
        node.setStyleMinHeight(model.minHeight);

        node.setStyleMaxWidth(model.maxWidth);
        node.setStyleMaxHeight(model.maxHeight);

        node.setSizeToFit(model.sizetofit);

        return node;
    }
}
