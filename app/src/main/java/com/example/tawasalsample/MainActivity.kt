package com.example.tawasalsample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.cats.model.Cat
import com.example.tawasalsample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val widget by lazy { binding.catsWidget }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        widget.start(catMocks)
    }
}

const val scottishFoldImage =
    "https://images.pexels.com/photos/4305016/pexels-photo-4305016.jpeg?cs=srgb&dl=pexels-ricky-liu-4305016.jpg&fm=jpg"
const val scottishImage =
    "https://images.pexels.com/photos/156934/pexels-photo-156934.jpeg?cs=srgb&dl=pexels-flickr-156934.jpg&fm=jpg"
const val exImage =
    "https://d17fnq9dkz9hgj.cloudfront.net/breed-uploads/2018/08/exotic-shorthair-card-large.jpg?bust=1535569793&width=900"

val catMocks = listOf(
    Cat(scottishImage, "Anton", "Hairless", "dangerous hairless cat, dangerous hairless cat, dangerous hairless cat, dangerous hairless cat"),
    Cat(scottishFoldImage, "Leon", "Scottish fold", "pretty kitty with funny ears"),
    Cat(scottishImage, "Barseek", "British", "cute kitten with funny tail"),
    Cat(scottishFoldImage, "Salem", "Speaking cat", "devilish black cat"),
    Cat(scottishFoldImage, "Garfield", "Fat", "soooooo fat cat, the biggest cat you ever seen"),
    Cat(exImage, "Oleg", "Exotic Shorthair", "cute kitten with funny nose"),
    Cat(scottishImage, "Abdullah", "British", "cute kitten with funny nose"),
)