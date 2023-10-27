package ee.lhv.aml.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sanctioned_person")
public class SanctionedPerson implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name_6", nullable = false)
    private String name6;

    @Column(name = "name_1")
    private String name1;

    @Column(name = "name_2")
    private String name2;

    @Column(name = "name_3")
    private String name3;

    @Column(name = "name_4")
    private String name4;

    @Column(name = "name_5")
    private String name5;

    @Column(name = "title")
    private String title;

    @Column(name = "name_non_latin_script")
    private String nameNonLatinScript;

    @Column(name = "non_latin_script_type")
    private String nonLatinScriptType;

    @Column(name = "non_latin_script_language")
    private String nonLatinScriptLanguage;

    @Column(name = "dob")
    private String dob;

    @Column(name = "town_of_birth")
    private String townOfBirth;

    @Column(name = "country_of_birth")
    private String countryOfBirth;

    @Column(name = "nationality")
    private String nationality;

    @Column(name = "passport_number")
    private String passportNumber;

    @Column(name = "passport_details")
    private String passportDetails;

    @Column(name = "national_identification_number")
    private String nationalIdentificationNumber;

    @Column(name = "national_identification_details")
    private String nationalIdentificationDetails;

    @Column(name = "position")
    private String position;

    @Column(name = "address_1")
    private String address1;

    @Column(name = "address_2")
    private String address2;

    @Column(name = "address_3")
    private String address3;

    @Column(name = "address_4")
    private String address4;

    @Column(name = "address_5")
    private String address5;

    @Column(name = "address_6")
    private String address6;

    @Column(name = "post_zip_code")
    private String postZipCode;

    @Column(name = "country")
    private String country;

    @Column(name = "other_information")
    private String otherInformation;

    @Column(name = "group_type")
    private String groupType;

    @Column(name = "alias_type")
    private String aliasType;

    @Column(name = "alias_quality")
    private String aliasQuality;

    @Column(name = "regime")
    private String regime;

    @Column(name = "listed_on")
    private String listedOn;

    @Column(name = "uk_sanctions_list_date_designated")
    private String ukSanctionsListDateDesignated;

    @Column(name = "last_updated")
    private String lastUpdated;

    @Column(name = "group_id")
    private Integer groupId;

    @Column(name = "create_dtime")
    private LocalDateTime createDtime;

    @Column(name = "update_dtime")
    private LocalDateTime updateDtime;
}