# CLONEPOOL

<p align="center"><img src="docs/images/logo_320.png" alt="logo"/></p>

<!-- [![GitHub stars](https://img.shields.io/github/stars/fand/clonepool.svg?style=social&label=Star)](https://github.com/fand/clonepool) -->

<p align="center">
Clonepool is a CLI utility to manage copies of git repositories.<br>
Created to reduce compilation times on Scala projects.
</p>

## Install

`$ brew install clonepool`

## Usage

```
$ clonepool                    # List all clones
$ clonepool my-branch          # Checkout to my-branch of the branch of current directory
$ clonepool my-repo            # Clone my-repo and checkout to the default branch
$ clonepool my-repo my-branch  # Clone my-repo and checkout to my-branch
```

Assume that you are gonna create a pull-request to [Play framework](https://github.com/playframework/playframework).  
Run this command in your terminal.

```
$ clonepool playframework/playframework my-branch
```

This is almost equivalent to running following commands.

```
$ ghq get playframework/playframework
$ cp -r $(ghq root)/github.com/playframework/playframework ~/.clonepool/github.com/playframework/playframework/my-branch
$ echo ~/.clonepool/github.com/playframework/playframework/my-branch
```

Clonepool clones the repository using [ghq](https://github.com/motemen/ghq).  
So you can develop in any branches without affecting class caches!

If you are already in a Git repository, you can omit the repository name:

```
$ clonepool my-branch
```

Clonepool doesn't have a command to clean the clones.
When you finished the branch, remove the clone by hand.

```
$ rm $(clonepool playframework/playframework my-branch)
```

## Use With peco

Clonepool works well with [peco](https://github.com/peco/peco)!  
Add these lines to your `~/.bashrc` (or `~/.zshrc`, etc).

```sh
function go_clone() {
    if [[ $1 == '' ]]; then
        cd $(clonepool | peco)
    else
        cd $(clonepool $1)
    fi
}
```

then `go_clone` works like magic!!


## Author

Written by [@amagitakayosi](https://twitter.com/amagitakayosi)

## License

MIT
