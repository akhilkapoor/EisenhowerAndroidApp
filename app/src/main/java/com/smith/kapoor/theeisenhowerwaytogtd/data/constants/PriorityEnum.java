/* Created by Kapoor and Smith */

package com.smith.kapoor.theeisenhowerwaytogtd.data.constants;

/**
 * Enum to keep track of Task Priority's
 */
public enum PriorityEnum {

    IMPORTANT_URGENT(1, "Important and Urgent"),
    IMPORTANT_NOT_URGENT(2, "Important and Not Urgent"),
    NOT_IMPORTANT_URGENT(3, "Not Important and Urgent"),
    NOT_IMPORTANT_NOT_URGENT(4, "Not Important and Not Urgent");

    private int priorityValue;
    private String text;

    PriorityEnum(int priorityValue, String text) {
        this.priorityValue = priorityValue;
        this.text = text;
    }

    public int getPriorityValue() {
        return priorityValue;
    }

    public String getText() {
        return text;
    }

    public static PriorityEnum getById(int priorityValue) {
        PriorityEnum val = null;
        if (priorityValue > 0 && priorityValue < 5) {
            for (PriorityEnum enumType : PriorityEnum.values()) {
                if (enumType.getPriorityValue() == priorityValue) {
                    val = enumType;
                }
            }
        }

        return val;
    }
}
