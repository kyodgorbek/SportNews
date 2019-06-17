package edgar.yodgorbek.yangiliklar.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity
class Source {
    @PrimaryKey
    @SerializedName("id")
    @Expose
    var id: String
    @SerializedName("name")
    @Expose
    var name: String? = null

    constructor() {}

    constructor(source: Source) {}

    constructor(source: String) {}
}