package smithy4s.codegen.internals

import smithy4s_codegen.generation.CodegenResult
import software.amazon.smithy.model.Model

import scala.jdk.CollectionConverters.*

object CodegenTrick {
  def run(
      model: Model
  ): List[(os.RelPath, CodegenResult)] =
    CodegenImpl
      .generate(
        model,
        model
          .getShapeIds()
          .asScala
          .map(_.getNamespace())
          .toSet
          .filterNot { ns =>
            Set("smithy.api", "smithy4s", "alloy")
              .exists(prefix => ns == prefix || ns.startsWith(s"$prefix."))
          }
          .toList
      )
      .map { case (p, r) => p -> CodegenResult(r.namespace, r.name, r.content) }
}
