/* Created by Kapoor and Smith */

package com.smith.kapoor.theeisenhowerwaytogtd.data.constants;

/**
 * Complete Enum to track a Task status.
 */
public enum CompletedEnum {

    INCOMPLETE(0),
    COMPLETED(1);

    private int value;

    CompletedEnum(int value) {
        this.value = value;
    }
}
