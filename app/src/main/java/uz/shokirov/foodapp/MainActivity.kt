package uz.shokirov.foodapp

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import uz.shokirov.foodapp.databinding.ActivityMainBinding
import uz.shokirov.foodapp.databinding.PostLayoutDialogBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    val TAG = "MainActivity"
    private lateinit var postAdapter: PostAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()



        getAllPosts()
        setUpRv()
        binding.imageAddPost.setOnClickListener {
            addPostDialog()
        }
        binding.imageDeletePost.setOnClickListener {
            lifecycleScope.launchWhenCreated {
                RetrofitInstance.retrofit.deletePost()
               var response =  RetrofitInstance.retrofit.deletePost()
                if (response.isSuccessful){
                    Toast.makeText(this@MainActivity, "Fake Post Deleted", Toast.LENGTH_SHORT).show()
                }else{
                    Log.d(TAG, "onCreate: ${response.code()}")
                }
            }
        }

    }

    private fun setUpRv() {
        postAdapter = PostAdapter()
        binding.rv.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = postAdapter
            setHasFixedSize(true)
        }
    }

    private fun getAllPosts() {

        lifecycleScope.launchWhenCreated {
            val response = RetrofitInstance.retrofit.getAllPosts()

            if (response.isSuccessful && response.body() != null) {
                postAdapter.differ.submitList(response.body())
                Log.d(TAG, "getAllPosts: getAllPosts: ${response.body()}")
            } else {
                Log.d(TAG, "getAllPosts: ${response.code()}")
            }

        }

    }

    private fun addPostDialog() {
        val mDialog = Dialog(this)
        val mBinding = PostLayoutDialogBinding.inflate(layoutInflater)
        mDialog.setContentView(mBinding.root)
        mBinding.btnCancel.setOnClickListener {
            mDialog.dismiss()
        }

        mBinding.btnsave.setOnClickListener {
            if (mBinding.edtPostBody.text.toString()
                    .isNotEmpty() && mBinding.edtTitle.text.toString().isNotEmpty()
            ) {
                val title = mBinding.edtTitle.text.toString().trim()
                val body = mBinding.edtPostBody.text.toString().trim()
                val userId = mBinding.edtUserId.text.toString().toInt()
                makePost(title,body,userId)
                Toast.makeText(this, "Post added", Toast.LENGTH_SHORT).show()
                mDialog.dismiss()
            } else {
                Toast.makeText(this, "Body and title must not empty", Toast.LENGTH_SHORT).show()
            }

        }

        mDialog.show()
    }

    private fun makePost(title:String,body:String,Id:Int) {
        lifecycleScope.launchWhenCreated {
            val post = PostItem(body,0,title,Id)
            val response = RetrofitInstance.retrofit.posting(post)

            if (response.isSuccessful && response.body()!=null){
                Log.d("", "OutPost: ${response.body()}")
            }else{
                Log.d("post_error", "Error: ${response.body()}")
            }

        }
    }

}