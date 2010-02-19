/*
 *  Copyright 2010 Mathieu ANCELIN.
 * 
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  under the License.
 */

package cx.ath.mancel01.dependencyshot.samples.vdm.model;

import java.util.Date;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * An object representation of VDM.
 *
 * @author Mathieu ANCELIN
 */
@XmlRootElement(name="vdm")
public class Vdm {
    /**
     * The id of the VDM.
     */
    private Long id;
    /**
     * The author of the VDM.
     */
    private String author;
    /**
     * The date of the VDM.
     */
    private Date date;
    /**
     * The validate number.
     */
    private Long iValidate;
    /**
     * The deserve number.
     */
    private Long youDeserveIt;
    /**
     * The number of comments.
     */
    private Long comments;
    /**
     * The commentable.
     */
    private Long commentable;
    /**
     * The text of the VDM.
     */
    private String text;
    /**
     * The category of the VDM.
     */
    private String categ;

    /**
     * @return get the author.
     */
    @XmlElement(name="auteur")
    public final String getAuthor() {
        return author;
    }
    /**
     * @param author set the author.
     */
    public final void setAuthor(String author) {
        this.author = author;
    }

    /**
     * @return get commentable.
     */
    @XmlElement(name="commentable")
    public final Long getCommentable() {
        return commentable;
    }

    /**
     * @param commentable set commentable.
     */
    public final void setCommentable(Long commentable) {
        this.commentable = commentable;
    }

    /**
     * @return get comments.
     */
    @XmlElement(name="commentaires")
    public final Long getComments() {
        return comments;
    }

    /**
     * @param comments set comments.
     */
    public final void setComments(Long comments) {
        this.comments = comments;
    }

    /**
     * @return get date.
     */
    @XmlElement(name="date")
    public final Date getDate() {
        return date;
    }

    /**
     * @param date set dates.
     */
    public final void setDate(Date date) {
        this.date = date;
    }

    /**
     * @return get validate.
     */
    @XmlElement(name="je_valide")
    public final Long getiValidate() {
        return iValidate;
    }

    /**
     * @param iValidate set validate.
     */
    public final void setiValidate(Long iValidate) {
        this.iValidate = iValidate;
    }

    /**
     * @return get ID.
     */
    @XmlAttribute(name="id")
    public final Long getId() {
        return id;
    }

    /**
     * @param id set id.
     */
    public final void setId(Long id) {
        this.id = id;
    }

    /**
     * @return get text.
     */
    @XmlElement(name="texte")
    public final String getText() {
        return text;
    }

    /**
     * @param text set text.
     */
    public final void setText(String text) {
        this.text = text;
    }

    /**
     * @return get deserve.
     */
    @XmlElement(name="bien_merite")
    public final Long getYouDeserveIt() {
        return youDeserveIt;
    }

    /**
     * @param youDeserveIt set deserve.
     */
    public final void setYouDeserveIt(Long youDeserveIt) {
        this.youDeserveIt = youDeserveIt;
    }

    /**
     * @return get categ.
     */
    @XmlElement(name="categorie")
    public final String getCateg() {
        return categ;
    }

    /**
     * @param categ set categ.
     */
    public final void setCateg(String categ) {
        this.categ = categ;
    }

    /**
     * Return the string representation of a VDM.
     * 
     * @return the string representation of a VDM.
     */
    @Override
    public final String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("id : " + this.id + "\n");
        builder.append("author : " + this.author + "\n");
        builder.append("categ : " + this.categ + "\n");
        builder.append("text : " + this.text + "\n");
        builder.append("commentable : " + this.commentable + "\n");
        builder.append("comments : " + this.comments + "\n");
        builder.append("I validate : " + this.iValidate + "\n");
        builder.append("you deserve it : " + this.youDeserveIt + "\n");
        builder.append("date : " + this.date + "\n");
        return builder.toString();
    }
}
