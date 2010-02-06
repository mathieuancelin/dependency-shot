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

import java.util.Collection;
import java.util.Date;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * An object representation of a random VDM rest service.
 *
 * @author Mathieu ANCELIN
 */
@XmlRootElement(name="root")
public class Random {
    /**
     * Key for the API.
     */
    private String activeKey;
    /**
     * Returned VDMs
     */
    private Collection<Vdm> vdms;
    /**
     * A code.
     */
    private Long code;
    /**
     * The publication date.
     */
    private Date pubdate;
    /**
     * Encoutered errors.
     */
    private Collection<String> errors;

    /**
     * @return get the activeKey
     */
    @XmlElement(name="active_key")
    public String getActiveKey() {
        return activeKey;
    }

    /**
     * @param activeKey set the activeKey
     */
    public void setActiveKey(String activeKey) {
        this.activeKey = activeKey;
    }

    /**
     * @return get code.
     */
    public Long getCode() {
        return code;
    }

    /**
     * @param code set code.
     */
    public void setCode(Long code) {
        this.code = code;
    }

    /**
     * @return get errors.
     */
    @XmlElementWrapper(name = "erreurs")
    @XmlElement(name="erreur")
    public Collection<String> getErrors() {
        return errors;
    }

    /**
     * @param errors set errors.
     */
    public void setErrors(Collection<String> errors) {
        this.errors = errors;
    }

    /**
     * @return get publication date.
     */
    public Date getPubdate() {
        return pubdate;
    }

    /**
     * @param pubdate set publication date.
     */
    public void setPubdate(Date pubdate) {
        this.pubdate = pubdate;
    }

    /**
     * @return get vdms.
     */
    @XmlElementWrapper(name = "vdms")
    @XmlElement(name="vdm")
    public Collection<Vdm> getVdms() {
        return vdms;
    }

    /**
     * @param vdms set the vdms.
     */
    public void setVdms(Collection<Vdm> vdms) {
        this.vdms = vdms;
    }
}
