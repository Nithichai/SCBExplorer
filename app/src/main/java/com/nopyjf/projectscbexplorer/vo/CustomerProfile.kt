package com.nopyjf.projectscbexplorer.vo


import com.google.gson.annotations.SerializedName

data class CustomerProfile(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("status")
    val status: Status
) {
    data class Data(
        @SerializedName("profile")
        val profile: Profile
    ) {
        data class Profile(
            @SerializedName("address")
            val address: Address,
            @SerializedName("alienID")
            val alienID: String,
            @SerializedName("birthDate")
            val birthDate: String,
            @SerializedName("citizenID")
            val citizenID: String,
            @SerializedName("email")
            val email: String,
            @SerializedName("engFirstName")
            val engFirstName: String,
            @SerializedName("engLastName")
            val engLastName: String,
            @SerializedName("genderCode")
            val genderCode: String,
            @SerializedName("mobile")
            val mobile: String,
            @SerializedName("partnerID")
            val partnerID: String,
            @SerializedName("passportNumber")
            val passportNumber: String,
            @SerializedName("thaiFirstName")
            val thaiFirstName: String,
            @SerializedName("thaiLastName")
            val thaiLastName: String
        ) {
            data class Address(
                @SerializedName("countryCode")
                val countryCode: String,
                @SerializedName("currentAddressFlag")
                val currentAddressFlag: String,
                @SerializedName("engAddressAmphur")
                val engAddressAmphur: String,
                @SerializedName("engAddressDistrict")
                val engAddressDistrict: String,
                @SerializedName("engAddressMoo")
                val engAddressMoo: String,
                @SerializedName("engAddressNumber")
                val engAddressNumber: String,
                @SerializedName("engAddressProvince")
                val engAddressProvince: String,
                @SerializedName("engAddressState")
                val engAddressState: String,
                @SerializedName("engAddressThanon")
                val engAddressThanon: String,
                @SerializedName("floorNumber")
                val floorNumber: String,
                @SerializedName("thaiAddressAmphur")
                val thaiAddressAmphur: String,
                @SerializedName("thaiAddressDistrict")
                val thaiAddressDistrict: String,
                @SerializedName("thaiAddressMoo")
                val thaiAddressMoo: String,
                @SerializedName("thaiAddressNumber")
                val thaiAddressNumber: String,
                @SerializedName("thaiAddressProvince")
                val thaiAddressProvince: String,
                @SerializedName("thaiAddressState")
                val thaiAddressState: String,
                @SerializedName("thaiAddressThanon")
                val thaiAddressThanon: String,
                @SerializedName("unitNumber")
                val unitNumber: String,
                @SerializedName("zipCode")
                val zipCode: String
            )
        }
    }

    data class Status(
        @SerializedName("code")
        val code: Int,
        @SerializedName("description")
        val description: String
    )
}