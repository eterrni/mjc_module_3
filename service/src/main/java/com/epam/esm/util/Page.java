package com.epam.esm.util;

import com.epam.esm.exception.InvalidDataExeception;

public class Page {
    private int page;
    private int size;
    private long countEntity;

    public Page(int page, int size, long countEntity) throws InvalidDataExeception {
        validateData(page, size, countEntity);
        this.page = page;
        this.size = size;
        this.countEntity = countEntity;
    }

    public int getOffset() {
        return (page - 1) * size;
    }

    public int getLimit() {
        return size;
    }

    private boolean validateData(int page, int size, long countEntity) {
        if (page < 0) {
            throw new InvalidDataExeception("The page number must not be less than zero.");
        }
        if (size < 1) {
            throw new InvalidDataExeception("The number of output entities must be greater than 0.");
        }
        if ((page - 1) * size > countEntity) {
            throw new InvalidDataExeception("The page with the number = " + page + " does not exist.");
        }
        return true;
    }
}
