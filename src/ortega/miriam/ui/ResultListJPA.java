/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ortega.miriam.ui;

import java.util.AbstractList;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author macbookpro
 */
class ResultListJPA<T> extends AbstractList<T> implements List<T> {

    private final Query rowCountQuery;
    private final Query getRowsQuery;
    private int startPosition;
    private int counter=0;
    private List<T> cache = null;

    ResultListJPA(Query rowCountQuery, Query getRowsQuery) {
        this.rowCountQuery = rowCountQuery;
        this.getRowsQuery = getRowsQuery;
        this.startPosition = 0;
        this.cache = getItems(startPosition, startPosition + 100);
    }

    public int size() { 
        return ((Long) rowCountQuery.getSingleResult()).intValue();
    }


    public T get(int rowIndex) {
        if ((rowIndex >= startPosition) && (rowIndex < (startPosition + 100))) {
        } else {
            this.cache = getItems(rowIndex, rowIndex + 100);
            this.startPosition = rowIndex;
        }
        T c = cache.get(rowIndex - startPosition);

        return c;
    }

    private List<T> getItems(int from, int to) {
        System.out.println("numer of requests to the database " + counter++);
        Query query = getRowsQuery.setMaxResults(to - from).setFirstResult(from); 
        //add the cache
        List<T> resultList = query.getResultList();
        return resultList;
    }
}
